package com.epam.esm.impl;

import com.epam.esm.GiftCertificateRepository;
import com.epam.esm.util.SelectFilterCreator;
import com.epam.esm.util.UpdateCreator;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateDTO;
import com.epam.esm.entity.Tag;
import com.epam.esm.mapper.CertificateRowMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@PropertySource("classpath:sql_query_certificate.properties")
public class GiftCertificateRepositoryImpl implements GiftCertificateRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private Environment environment;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String INSERT_CERTIFICATE = "INSERT_CERTIFICATE";
    private static final String DELETE_CERTIFICATE_BY_ID = "DELETE_CERTIFICATE_BY_ID";
    private static final String SELECT_CERTIFICATE = "SELECT_CERTIFICATE";
    private static final String SELECT_CERTIFICATE_BY_ID = "SELECT_CERTIFICATE_BY_ID";
    private static final String SELECT_CERTIFICATE_BY_NAME = "SELECT_CERTIFICATE_BY_NAME";
    private static final String INSERT_TAG_TO_CERTIFICATE = "INSERT_TAG_TO_CERTIFICATE";

    @Override
    public Optional<GiftCertificate> create(GiftCertificate certificate) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(environment.getProperty(INSERT_CERTIFICATE), new String[]{"id"});
                    ps.setString(1, certificate.getName());
                    ps.setString(2, certificate.getDescription());
                    ps.setBigDecimal(3, certificate.getPrice());
                    ps.setTimestamp(4, Timestamp.valueOf(ZonedDateTime.now().toLocalDateTime()));
                    ps.setString(5, ZonedDateTime.now().getZone().toString());
                    ps.setTimestamp(6, Timestamp.valueOf(ZonedDateTime.now().toLocalDateTime()));
                    ps.setString(7, ZonedDateTime.now().getZone().toString());
                    ps.setInt(8, certificate.getDuration());
                    return ps;
                },
                keyHolder);
        return getGiftCertificateByKeyHolder(keyHolder);
    }

    @Override
    public boolean delete(long id) {
        return jdbcTemplate.update(environment.getProperty(DELETE_CERTIFICATE_BY_ID), id) != 0 ? true : false;
    }

    @Override
    public Optional<GiftCertificate> update(GiftCertificateDTO giftCertificate) {
        giftCertificate.setLastUpdateDate(Timestamp.valueOf(ZonedDateTime.now().toLocalDateTime()));
        giftCertificate.setTimeZone_LastUpdateDate(ZonedDateTime.now().getZone().toString());

        UpdateCreator<GiftCertificateDTO> updateCreator = new UpdateCreator<>(giftCertificate, objectMapper);
        Map<String, Object> map = updateCreator.getConvertedMap();
        SqlParameterSource parameters = new MapSqlParameterSource().addValues(map);
        String query = updateCreator.getSqlUpdateQuery(new GiftCertificate());
        namedParameterJdbcTemplate.update(query, parameters);

        return getGiftCertificateById(giftCertificate.getId());
    }

    @Override
    public Optional<GiftCertificate> findById(long id) {
       return getGiftCertificateById(id);
    }

    @Override
    public Optional<GiftCertificate> findByName(String name) {
        GiftCertificate certificate = DataAccessUtils.singleResult(
                jdbcTemplate.query(environment.getProperty(SELECT_CERTIFICATE_BY_NAME),
                        new CertificateRowMapper(), name));
        return Optional.ofNullable(certificate);
    }

    @Override
    public Optional<GiftCertificate> addTagToCertificate(List<Tag> tagList, long id) {
        for (Tag tag : tagList) {
            jdbcTemplate.update(environment.getProperty(INSERT_TAG_TO_CERTIFICATE), tag.getName(), id);
        }
        return getGiftCertificateById(id);
    }

    @Override
    public List<GiftCertificate> getAll() {
        return jdbcTemplate.query(environment.getProperty(SELECT_CERTIFICATE), new CertificateRowMapper());
    }

    @Override
    public List<GiftCertificate> filterCertificate(Map<String, String> filterParam) {
        SelectFilterCreator query = new SelectFilterCreator();
        return jdbcTemplate.query(query.createFilterQuery(filterParam), new CertificateRowMapper());
    }

    private Optional<GiftCertificate> getGiftCertificateByKeyHolder(KeyHolder keyHolder) {
        Number id = keyHolder.getKey();
        return id != null ? findById(id.longValue()) : Optional.empty();
    }

    private Optional<GiftCertificate> getGiftCertificateById(long id) {
        GiftCertificate certificate = DataAccessUtils.singleResult(
                jdbcTemplate.query(environment.getProperty(SELECT_CERTIFICATE_BY_ID),
                        new CertificateRowMapper(), id));
        return Optional.ofNullable(certificate);
    }
}

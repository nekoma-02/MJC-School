package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateRepository;
import com.epam.esm.dao.UpdateCreator;
import com.epam.esm.dao.mapper.CertificateRowMapper;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateDTO;
import com.epam.esm.entity.Tag;
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
    private static final String INSERT_TAG_TO_CERTIFICATE = "INSERT_TAG_TO_CERTIFICATE";


    @Override
    public boolean create(GiftCertificate certificate) {

        return jdbcTemplate.update(environment.getProperty(INSERT_CERTIFICATE),
                certificate.getName(),
                certificate.getDescription(),
                certificate.getPrice(),
                Timestamp.valueOf(ZonedDateTime.now().toLocalDateTime()),
                ZonedDateTime.now().getZone().toString(),
                Timestamp.valueOf(ZonedDateTime.now().toLocalDateTime()),
                ZonedDateTime.now().getZone().toString(),
                certificate.getDuration()) == 1;

    }

    @Override
    public boolean delete(long id) {
        return jdbcTemplate.update(environment.getProperty(DELETE_CERTIFICATE_BY_ID), id) == 1 ? true : false;
    }

    @Override
    public Optional<GiftCertificate> update(GiftCertificateDTO giftCertificate) {
        KeyHolder holder = new GeneratedKeyHolder();

        giftCertificate.setLastUpdateDate(Timestamp.valueOf(ZonedDateTime.now().toLocalDateTime()));
        giftCertificate.setTimeZone_LastUpdateDate(ZonedDateTime.now().getZone().toString());

        UpdateCreator<GiftCertificateDTO> updateCreator = new UpdateCreator<>(giftCertificate, objectMapper);

        Map<String, Object> map = updateCreator.getConvertedMap();
        SqlParameterSource parameters = new MapSqlParameterSource().addValues(map);

        String query = updateCreator.getSqlUpdateQuery(new GiftCertificate());
        namedParameterJdbcTemplate.update(query, parameters, holder);

        return giftCertificateByKeyHolder(holder);
    }

    @Override
    public Optional<GiftCertificate> findById(long id) {
        GiftCertificate certificate = DataAccessUtils.singleResult(jdbcTemplate.query(environment.getProperty(SELECT_CERTIFICATE_BY_ID), new CertificateRowMapper(), id));
        return Optional.ofNullable(certificate);
    }

    @Override
    public void addTagToCertificate(List<Tag> tagList, long id) {
        for (Tag tag : tagList) {
            jdbcTemplate.update(environment.getProperty(INSERT_TAG_TO_CERTIFICATE), tag.getName(), id);
        }

    }

    @Override
    public List<GiftCertificate> getAll() {
        return jdbcTemplate.query(environment.getProperty(SELECT_CERTIFICATE), new CertificateRowMapper());
    }

    private Optional<GiftCertificate> giftCertificateByKeyHolder(KeyHolder keyHolder) {
        Number id = keyHolder.getKey();
        return id != null ? findById(id.longValue()) : Optional.empty();
    }
}

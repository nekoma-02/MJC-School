package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateRepository;
import com.epam.esm.dao.UpdateCreator;
import com.epam.esm.dao.mapper.CertificateRowMapper;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateDTO;
import com.epam.esm.entity.Tag;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class GiftCertificateRepositoryImpl implements GiftCertificateRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String INSERT_CERTIFICATE = "insert into GiftCertificate(GiftCertificate.Name,GiftCertificate.Description,Price,CreateDate,TimeZone_CreateDate,LastUpdateDate,TimeZone_LastUpdateDate,Duration) value (?,?,?,?,?,?,?,?)";
    private static final String DELETE_CERTIFICATE_BY_ID = "delete from GiftCertificate where id = ?";
    private static final String SELECT_CERTIFICATE = "select GiftCertificate.id as gif_id, GiftCertificate.Name as gif_name ," +
            " GiftCertificate.Description as gift_description,Price, CreateDate, TimeZone_CreateDate,LastUpdateDate," +
            "TimeZone_LastUpdateDate, Duration, group_concat(Tag_id, ' ' ,Tag.Name) as tags " +
            "from Tag_has_GiftCertificate right join GiftCertificate on Tag_has_GiftCertificate.GiftCertificate_id = GiftCertificate.id " +
            "left join Tag on Tag_has_GiftCertificate.Tag_id = Tag.id group by gif_id";

    private static final String SELECT_CERTIFICATE_BY_ID = "select GiftCertificate.id as gif_id, GiftCertificate.Name as gif_name , " +
            "GiftCertificate.Description as gift_description,Price, CreateDate, " +
            "TimeZone_CreateDate,LastUpdateDate,TimeZone_LastUpdateDate, Duration, " +
            "group_concat(Tag_id , ' ' ,Tag.Name) as tags " +
            "from Tag_has_GiftCertificate right join GiftCertificate on Tag_has_GiftCertificate.GiftCertificate_id = GiftCertificate.id " +
            "left join Tag on Tag_has_GiftCertificate.Tag_id = Tag.id " +
            "where GiftCertificate.id = ? group by gif_id ";

    private static final String INSERT_TAG_TO_CERTIFICATE = "insert into Tag_has_GiftCertificate value ((select id from Tag where Name = ?),?)";


    @Override
    public Optional<GiftCertificate> create(GiftCertificate certificate) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(INSERT_CERTIFICATE,
                        certificate.getName(),
                        certificate.getDescription(),
                        certificate.getPrice(),
                        Timestamp.valueOf(ZonedDateTime.now().toLocalDateTime()),
                        ZonedDateTime.now().getZone().toString(),
                        Timestamp.valueOf(ZonedDateTime.now().toLocalDateTime()),
                        ZonedDateTime.now().getZone().toString(),
                        certificate.getDuration());

        System.out.println(keyHolder.getKey());
        return giftCertificateByKeyHolder(keyHolder);

    }

    @Override
    public boolean delete(long id) {
        return jdbcTemplate.update(DELETE_CERTIFICATE_BY_ID, id) == 1 ? true : false;
    }

    @Override
    public Optional<GiftCertificate> update(GiftCertificateDTO giftCertificate) {
        KeyHolder holder = new GeneratedKeyHolder();

        giftCertificate.setLastUpdateDate(Timestamp.valueOf(ZonedDateTime.now().toLocalDateTime()));
        giftCertificate.setTimeZone_LastUpdateDate(ZonedDateTime.now().getZone().toString());

        UpdateCreator<GiftCertificateDTO> updateCreator = new UpdateCreator<>(giftCertificate,objectMapper);

        Map<String,Object> map = updateCreator.getConvertedMap();


        SqlParameterSource parameters = new MapSqlParameterSource().addValues(map);

        String query = updateCreator.getSqlUpdateQuery(new GiftCertificate());

        namedParameterJdbcTemplate.update(query,parameters,holder);


        return giftCertificateByKeyHolder(holder);
    }

    @Override
    public Optional<GiftCertificate> findById(long id) {
        GiftCertificate certificate = DataAccessUtils.singleResult(jdbcTemplate.query(SELECT_CERTIFICATE_BY_ID, new CertificateRowMapper(), id));
        return Optional.ofNullable(certificate);
    }

    @Override
    @Transactional
    public void addTagToCertificate(List<Tag> tagList, long id) {
        for (Tag tag : tagList) {
            jdbcTemplate.update(INSERT_TAG_TO_CERTIFICATE, tag.getName(), id);
        }

    }

    @Override
    public List<GiftCertificate> getAll() {
        return jdbcTemplate.query(SELECT_CERTIFICATE, new CertificateRowMapper());
    }

    private Optional<GiftCertificate> giftCertificateByKeyHolder(KeyHolder keyHolder) {
        Number id = keyHolder.getKey();
        return id != null ? findById(id.longValue()) : Optional.empty();
    }
}

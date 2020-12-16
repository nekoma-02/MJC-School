package com.epam.esm;

import com.epam.esm.config.SpringConfig;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Pagination;
import com.epam.esm.entity.Tag;
import com.epam.esm.impl.GiftCertificateRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.web.WebAppConfiguration;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@SpringJUnitConfig(SpringConfig.class)
@Sql({"classpath:drop_schema.sql", "classpath:create_schema.sql"})
@DataJpaTest
@EnableAutoConfiguration
@ActiveProfiles("test")
public class GiftCertificateRepositoryTest {

    private List<GiftCertificate> certificateList;
    private GiftCertificate certificate1;
    private GiftCertificate certificate2;
    private GiftCertificate certificate3;
    private Pagination pagination;
    private ZonedDateTime dateTime = ZonedDateTime.parse("2020-10-10T10:10:10+03:00[Europe/Moscow]");

    private GiftCertificateRepository repo = new GiftCertificateRepositoryImpl();

    @BeforeEach
    public void setUp() {
        pagination = new Pagination(5,0);
        certificate3 = GiftCertificate.builder()
                .id(1)
                .name("name3")
                .price(BigDecimal.valueOf(12.2))
                .description("desc3")
                .createDate(dateTime.toLocalDateTime())
                .createDateTimeZone(dateTime.getZone())
                .lastUpdateDate(dateTime.toLocalDateTime())
                .lastUpdateDateTimeZone(dateTime.getZone())
                .duration(12)
                .tagSet(new HashSet<>(Arrays.asList(new Tag(1, "rock"), new Tag(2, "music"))))
                .build();
        certificate1 = GiftCertificate.builder()
                .id(1)
                .name("name1")
                .price(BigDecimal.valueOf(12.2))
                .description("desc1")
                .createDate(dateTime.toLocalDateTime())
                .createDateTimeZone(dateTime.getZone())
                .lastUpdateDate(dateTime.toLocalDateTime())
                .lastUpdateDateTimeZone(dateTime.getZone())
                .duration(12)
                .tagSet(new HashSet<>(Arrays.asList(new Tag(1, "rock"), new Tag(2, "music"))))
                .build();
        certificate2 = GiftCertificate.builder()
                .id(1)
                .name("name2")
                .price(BigDecimal.valueOf(10.2))
                .description("desc2")
                .createDate(dateTime.toLocalDateTime())
                .createDateTimeZone(dateTime.getZone())
                .lastUpdateDate(dateTime.toLocalDateTime())
                .lastUpdateDateTimeZone(dateTime.getZone())
                .duration(12)
                .tagSet(new HashSet<>(Arrays.asList(new Tag(1, "rock"))))
                .build();
        certificateList = Arrays.asList(certificate1, certificate2);
    }


    @Test
    void getCertificateList_whenCertificatesExists_thenReturnList() {
        Assertions.assertIterableEquals(certificateList, repo.getAll(pagination));
    }

    @Test
    public void findById_whenCertificateExist_thenReturnOptionalCertificate() {
        Assertions.assertEquals(certificate1, repo.findById(certificate1.getId()));
    }

    @Test
    public void findById_whenCertificateNotExists_thenReturnNull() {
        Assertions.assertEquals(null, repo.findById(99));
    }

    @Test
    public void create_whenCertificateDoesntExist_thenReturnTrue() {
        Assertions.assertEquals(Optional.of(certificate3),repo.create(certificate3));
    }

    @Test
    public void create_whenCertificateIsNull_thenReturnNullPointerException() {
        Assertions.assertThrows(NullPointerException.class, () -> repo.create(null));
    }

    @Test
    public void delete_whenCertificateExist_thenReturnTrue() {
        //Assertions.assertTrue(repo.delete(2));
    }

    @Test
    public void delete_whenCertificateNotExists_thenReturnFalse() {
        repo.delete(44);
        Assertions.assertIterableEquals(certificateList, repo.getAll(pagination));
    }

    @Test
    public void update_whenCertificateExist_thenReturnOptionalCertificate() {
        certificate1.setName("Sport");
        Assertions.assertEquals(certificate1.getName(), repo.update(certificate1));
    }

    @Test
    public void update_whenCertificateNotExists_thenReturnOptionalEmpty() {
        Assertions.assertEquals(Optional.empty(), repo.update(GiftCertificate.builder().build()));
    }

}

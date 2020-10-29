package com.epam.esm;

import com.epam.esm.config.H2Config;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateDTO;
import com.epam.esm.entity.Tag;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.web.WebAppConfiguration;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Sql({"classpath:drop_schema.sql", "classpath:create_schema.sql"})
@SpringJUnitConfig(H2Config.class)
@WebAppConfiguration
public class GiftCertificateRepositoryTest {

    private List<GiftCertificate> certificateList;
    private GiftCertificate certificate1;
    private GiftCertificate certificate2;
    private GiftCertificate certificate3;
    private GiftCertificateDTO giftCertificateDTO;
    private ZonedDateTime dateTime = ZonedDateTime.parse("2020-10-10T10:10:10+03:00[Europe/Moscow]");
    @Autowired
    private GiftCertificateRepository repo;

    @BeforeEach
    public void setUp() {
        giftCertificateDTO = GiftCertificateDTO.builder()
                .id(1)
                .name("Sport")
                .duration(12)
                .build();
        certificate3 = GiftCertificate.builder()
                .id(3)
                .name("name3")
                .price(BigDecimal.valueOf(12.2))
                .description("desc3")
                .createDate(dateTime)
                .lastUpdateDate(dateTime)
                .duration(12)
                .tagList(Arrays.asList(new Tag(1, "rock"), new Tag(2, "music")))
                .build();
        certificate1 = GiftCertificate.builder()
                .id(1)
                .name("name1")
                .price(BigDecimal.valueOf(12.2))
                .description("desc1")
                .createDate(dateTime)
                .lastUpdateDate(dateTime)
                .duration(12)
                .tagList(Arrays.asList(new Tag(1, "rock"), new Tag(2, "music")))
                .build();
        certificate2 = GiftCertificate.builder()
                .id(2)
                .name("name2")
                .price(BigDecimal.valueOf(12.2))
                .description("desc2")
                .createDate(dateTime)
                .lastUpdateDate(dateTime)
                .duration(12)
                .tagList(Arrays.asList(new Tag(1, "rock")))
                .build();
        certificateList = Arrays.asList(certificate1, certificate2);
    }


    @Test
    void getCertificateList_whenCertificatesExists_thenReturnList() {
        Assertions.assertIterableEquals(certificateList, repo.getAll());
    }

    @Test
    public void findById_whenCertificateExist_thenReturnOptionalCertificate() {
        Assertions.assertEquals(Optional.of(certificate1), repo.findById(certificate1.getId()));
    }

    @Test
    public void findById_whenCertificateNotExists_thenReturnOptionalEmpty() {
        Assertions.assertEquals(Optional.empty(), repo.findById(99));
    }

    @Test
    public void create_whenCertificateDoesntExist_thenReturnTrue() {
        Assertions.assertTrue(repo.create(certificate3));
    }

    @Test
    public void create_whenCertificateIsNull_thenReturnNullPointerException() {
        Assertions.assertThrows(NullPointerException.class, () -> repo.create(null));
    }


    @Test
    public void delete_whenCertificateExist_thenReturnTrue() {
        Assertions.assertTrue(repo.delete(2));
    }

    @Test
    public void delete_whenCertificateNotExists_thenReturnFalse() {
        Assertions.assertFalse(repo.delete(99));
    }

    @Test
    public void update_whenCertificateExist_thenReturnOptionalCertificate() {
        certificate1.setName("Sport");
        Assertions.assertEquals(certificate1.getName(), repo.update(giftCertificateDTO).get().getName());
    }

    @Test
    public void update_whenCertificateNotExists_thenReturnOptionalEmpty() {
        Assertions.assertEquals(Optional.empty(), repo.update(GiftCertificateDTO.builder().id(99).build()));
    }

}

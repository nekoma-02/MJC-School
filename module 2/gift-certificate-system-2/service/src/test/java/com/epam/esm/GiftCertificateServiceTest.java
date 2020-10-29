package com.epam.esm;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateDTO;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.GiftCertificateNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.web.WebAppConfiguration;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@SpringJUnitConfig(TestConfig.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@WebAppConfiguration
public class GiftCertificateServiceTest {
    private List<GiftCertificate> certificateList;
    private GiftCertificate certificate1;
    private GiftCertificate certificate2;
    private GiftCertificateDTO giftCertificateDTO;

    @Mock
    private GiftCertificateRepository repo;

    @Autowired
    @InjectMocks
    private GiftCertificateService service;

    @BeforeEach
    public void setUp() {
        giftCertificateDTO = GiftCertificateDTO.builder()
                .id(1)
                .name("Sport")
                .duration(12)
                .build();
        certificate1 = GiftCertificate.builder()
                .id(1)
                .name("Sport")
                .price(BigDecimal.valueOf(12.2))
                .description("desc1")
                .createDate(ZonedDateTime.now().minusDays(2))
                .lastUpdateDate(ZonedDateTime.now())
                .duration(12)
                .tagList(Arrays.asList(new Tag(1, "#rock"), new Tag(2, "#travel")))
                .build();
        certificate2 = GiftCertificate.builder()
                .id(2)
                .name("Park")
                .price(BigDecimal.valueOf(12.2))
                .description("desc1")
                .createDate(ZonedDateTime.now())
                .lastUpdateDate(ZonedDateTime.now())
                .duration(12)
                .tagList(Arrays.asList(new Tag(1, "#spa"), new Tag(2, "#relax")))
                .build();
        certificateList = Arrays.asList(certificate1, certificate2);
    }

    @Test
    public void getAllCertificates() {
        Mockito.when(repo.getAll()).thenReturn(certificateList);
        Assertions.assertIterableEquals(certificateList, service.getAll());
    }

    @Test
    public void findById_whenCertificateExist_thenReturnCertificate() {
        Mockito.when(repo.findById(certificate1.getId())).thenReturn(Optional.of(certificate1));
        Assertions.assertEquals(Optional.of(certificate1), service.findById(certificate1.getId()));
    }

    @Test
    public void findById_whenCertificateNotExists_theNotFoundException() {
        Mockito.when(repo.findById(0)).thenThrow(GiftCertificateNotFoundException.class);
        Assertions.assertThrows(GiftCertificateNotFoundException.class, () -> service.findById(0));
    }

    @Test
    public void update_whenCertificateNotExists_thenNotFoundException() {
        GiftCertificateDTO certificate = GiftCertificateDTO.builder().id(0).build();
        Mockito.when(repo.update(certificate)).thenThrow(GiftCertificateNotFoundException.class);
        Assertions.assertThrows(GiftCertificateNotFoundException.class, () -> service.update(certificate));
    }

    @Test
    void delete_whenCertificateNotExists_thenNotFoundException() {
        int id = 0;
        Mockito.when(repo.delete(id)).thenThrow(GiftCertificateNotFoundException.class);
        Assertions.assertThrows(GiftCertificateNotFoundException.class, () -> service.delete(id));
    }

    @Test
    public void sortByNameAsc_whenCertificateListEmpty_thenReturnEmptyList() {
        List<GiftCertificate> actual = service.getFilteredListCertificates("Sport", "name_asc", Collections.emptyList());
        Assertions.assertIterableEquals(Collections.emptyList(), actual);
    }

    @Test
    public void sortByNameAsc_whenCertificateListNull_thenReturnEmptyList() {
        List<GiftCertificate> actual = service.getFilteredListCertificates("Sport", "name_asc", null);
        Assertions.assertIterableEquals(Collections.emptyList(), actual);
    }

    @Test
    public void sortByNameDesc_whenCertificateListEmpty_thenReturnEmptyList() {
        List<GiftCertificate> actual = service.getFilteredListCertificates("Sport", "name_desc", Collections.emptyList());
        Assertions.assertIterableEquals(Collections.emptyList(), actual);
    }

    @Test
    public void sortByNameDesc_whenCertificateListNull_thenReturnEmptyList() {
        List<GiftCertificate> actual = service.getFilteredListCertificates("Sport", "name_desc", null);
        Assertions.assertIterableEquals(Collections.emptyList(), actual);
    }

    @Test
    public void sortByDateAsc_whenCertificateListEmpty_thenReturnEmptyList() {
        List<GiftCertificate> actual = service.getFilteredListCertificates("Sport", "date_asc", Collections.emptyList());
        Assertions.assertIterableEquals(Collections.emptyList(), actual);
    }

    @Test
    public void sortByDateAsc_whenCertificateListNull_thenReturnEmptyList() {
        List<GiftCertificate> actual = service.getFilteredListCertificates("Sport", "date_asc", null);
        Assertions.assertIterableEquals(Collections.emptyList(), actual);
    }

    @Test
    public void sortByDateDesc_whenCertificateListEmpty_thenReturnEmptyList() {
        List<GiftCertificate> actual = service.getFilteredListCertificates("Sport", "date_desc", Collections.emptyList());
        Assertions.assertIterableEquals(Collections.emptyList(), actual);
    }

    @Test
    public void sortByDateDesc_whenCertificateListNull_thenReturnEmptyList() {
        List<GiftCertificate> actual = service.getFilteredListCertificates("Sport", "date_desc", null);
        Assertions.assertIterableEquals(Collections.emptyList(), actual);
    }

    @Test
    public void sortByNameAsc_whenCertificateListIsNotNullAndSortTypeIsNameAsc_thenReturnList() {
        List<GiftCertificate> actual = service.getFilteredListCertificates(null, "name_asc", certificateList);
        List<GiftCertificate> expected = Arrays.asList(certificate2, certificate1);

        Assertions.assertIterableEquals(expected, actual);
    }

    @Test
    public void sortByNameDesc_whenCertificateListIsNotNullAndSortTypeIsNameDesc_thenReturnList() {
        List<GiftCertificate> actual = service.getFilteredListCertificates(null, "name_desc", certificateList);
        List<GiftCertificate> expected = Arrays.asList(certificate1, certificate2);

        Assertions.assertIterableEquals(expected, actual);
    }

    @Test
    public void sortByDateAsc_whenCertificateListIsNotNullAndSortTypeIsDateAsc_thenReturnList() {
        List<GiftCertificate> actual = service.getFilteredListCertificates(null, "date_asc", certificateList);
        List<GiftCertificate> expected = Arrays.asList(certificate1, certificate2);

        Assertions.assertIterableEquals(expected, actual);
    }

    @Test
    public void sortByDateDesc_whenCertificateListIsNotNullAndSortTypeIsDateDesc_thenReturnList() {
        List<GiftCertificate> actual = service.getFilteredListCertificates(null, "date_desc", certificateList);
        List<GiftCertificate> expected = Arrays.asList(certificate2, certificate1);

        Assertions.assertIterableEquals(expected, actual);
    }


}
package com.epam.esm;

import com.epam.esm.config.SpringConfig;
import com.epam.esm.entity.Pagination;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.impl.TagServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringJUnitConfig(SpringConfig.class)
@SpringBootTest
@EnableConfigurationProperties
@ActiveProfiles("test")
public class TagServiceTest {
    private List<Tag> tagList;
    private Tag tag1;
    private Tag tag2;
    private Pagination pagination;

    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    private TagService tagService = new TagServiceImpl();

    @BeforeEach
    public void setUp() {
        pagination = new Pagination(5, 0);
        tag1 = new Tag(1, "tag1");
        tag2 = new Tag(2, "tag2");
        tagList = Arrays.asList(tag1, tag2);
    }

    @Test
    public void getAllTags() {
        Mockito.when(tagRepository.getAll(pagination)).thenReturn(tagList);
        Assertions.assertIterableEquals(tagList, tagService.getAll(pagination));
    }

    @Test
    public void findTagById_whenTagExist_thenReturnTag() {
        long tagId = 1;
        Mockito.when(tagRepository.findById(tagId)).thenReturn(tag1);
        Tag expected = tag1;
        Tag actual = tagService.findById(tagId);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void findTagById_whenTagNotExisting_thenTagNotFoundException() {
        long tagId = 0;
        Mockito.when(tagRepository.findById(tagId)).thenReturn(null);
        Assertions.assertThrows(EntityNotFoundException.class, () -> tagService.findById(tagId));
    }

    @Test
    public void createTag_whenCreated_thenReturnTag() {
        Mockito.when(tagRepository.create(tag1)).thenReturn(tag1);
        Assertions.assertEquals(Optional.of(tag1).get(),tagService.create(tag1));
    }
}

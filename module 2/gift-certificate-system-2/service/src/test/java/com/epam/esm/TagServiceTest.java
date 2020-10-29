package com.epam.esm;

import com.epam.esm.entity.Tag;
import com.epam.esm.exception.TagNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@SpringJUnitConfig(TestConfig.class)
@WebAppConfiguration
public class TagServiceTest {

    private List<Tag> tagList;
    private Tag tag1;
    private Tag tag2;

    @Mock
    private TagRepository tagRepository;

    @Autowired
    @InjectMocks
    private TagService tagService;

    @BeforeEach
    void setUp() {
        tag1 = new Tag(1, "tag1");
        tag2 = new Tag(2, "tag2");
        tagList = Arrays.asList(tag1, tag2);
    }

    @Test
    void getAllTags() {
        Mockito.when(tagRepository.getAll()).thenReturn(tagList);
        Assertions.assertIterableEquals(tagList, tagService.getAll());
    }

    @Test
    void findTagById_whenTagExist_thenReturnTag() {
        long tagId = 1;
        Mockito.when(tagRepository.findById(tagId)).thenReturn(Optional.of(tag1));
        Tag expected = tag1;
        Tag actual = tagService.findById(tagId);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findTagById_whenTagNotExisting_thenTagNotFoundException() {
        long tagId = 0;
        Mockito.when(tagRepository.findById(tagId)).thenReturn(Optional.empty());
        Assertions.assertThrows(TagNotFoundException.class, () -> tagService.findById(tagId));
    }

    @Test
    void createTag_whenCreated_thenTrue() {
        Mockito.when(tagRepository.create(tag1)).thenReturn(true);
        Assertions.assertTrue(tagService.create(tag1));
    }
}

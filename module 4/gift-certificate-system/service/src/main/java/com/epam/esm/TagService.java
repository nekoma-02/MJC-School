package com.epam.esm;

import com.epam.esm.entity.Pagination;
import com.epam.esm.entity.Tag;
import java.util.List;

/**
 * The interface Tag service.
 */
public interface TagService {
    /**
     * This method is used to create the tag
     *
     * @param tag the tag to be created
     * @return true if tag was created, false if it were not
     */
    Tag create(Tag tag);

    /**
     * This method is used to delete the tag by name
     *
     * @param name the name of tag to be deleted
     * @throws com.epam.esm.exception.EntityNotFoundException if tag does not exist
     */
    void delete(long id);

    /**
     * This method is used to return tag by id
     *
     * @param id the id of tag to be returned
     * @return Tag
     * @throws com.epam.esm.exception.EntityNotFoundException if tag was not found
     */
    Tag findById(long id);

    /**
     * This method is used to return tag by name
     *
     * @param name name of tag to be returned
     * @return Tag
     * @throws com.epam.esm.exception.EntityNotFoundException if tag was not found
     */
    Tag findByName(String name);

    /**
     * This method is used to return the list of tags
     *
     * @return List of all tags or empty List if
     * no certificates were found
     */
    List<Tag> getAll(Pagination pagination);
}

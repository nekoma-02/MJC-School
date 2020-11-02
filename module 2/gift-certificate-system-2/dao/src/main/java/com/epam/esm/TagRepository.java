package com.epam.esm;

import com.epam.esm.entity.Tag;
import java.util.List;
import java.util.Optional;

/**
 * The interface Tag service.
 */
public interface TagRepository {
    /**
     * This method is used to create the tag
     *
     * @param tag the tag to be created
     * @return true if tag was created, false if it were not
     */
    Optional<Tag> create(Tag tag);

    /**
     * This method is used to delete the tag by name
     *
     * @param name the name of tag to be deleted
     * @return true if tag was deleted, false if it were not
     */
    boolean delete(String name);

    /**
     * This method is used to return tag by id
     *
     * @param id the id of tag to be returned
     * @return Optional tag or Optional empty if tag doesnt exist
     */
    Optional<Tag> findById(long id);

    /**
     * This method is used to return tag by name
     *
     * @param name name of tag to be returned
     * @return Optional tag or Optional empty if tag doesnt exist
     */
    Optional<Tag> findByName(String name);

    /**
     * This method is used to return the list of tags
     *
     * @return List of all tags or empty List if
     * no certificates were found
     */
    List<Tag> getAll();
}

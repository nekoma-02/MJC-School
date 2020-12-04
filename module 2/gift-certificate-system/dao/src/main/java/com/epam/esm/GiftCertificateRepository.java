package com.epam.esm;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateDTO;
import com.epam.esm.entity.Tag;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The interface Certificate repository.
 */
public interface GiftCertificateRepository {
    /**
     * This method is used to create the certificate
     *
     * @param certificate the certificate to be created
     * @return an Optional with the value of create certificate
     */
    Optional<GiftCertificate> create(GiftCertificate certificate);

    /**
     * This method is used to delete the certificate by id
     *
     * @param id the id of certificate to be deleted
     * @return an Optional with the value of delete certificate
     */
    boolean delete(long id);

    /**
     * This method is used to update the certificate
     *
     * @param giftCertificate the certificate to be updated
     * @return an Optional with the value of updated certificate
     */
    Optional<GiftCertificate> update(GiftCertificateDTO giftCertificate);

    /**
     * This method is used to return certificate by id
     *
     * @param id the id of certificate to be returned
     * @return on Optional with the value of find Certificate
     */
    Optional<GiftCertificate> findById(long id);

    /**
     * This method is used to return certificate by name
     *
     * @param name the name of certificate to be returned
     * @return on Optional with the value of find Certificate
     */
    Optional<GiftCertificate> findByName(String name);

    /**
     * This method is used for add tag to certificate
     *
     * @param tagList the list with tags value
     * @param id the id of certificate for binding tags
     */
    Optional<GiftCertificate> addTagToCertificate(List<Tag> tagList, long id);

    /**
     * This method is used to return the list of certificates
     *
     * @return List of all certificates or empty List if
     * no certificates were found
     */
    List<GiftCertificate> getAll();

    List<GiftCertificate> filterCertificate(Map<String,String> filterParam);
}

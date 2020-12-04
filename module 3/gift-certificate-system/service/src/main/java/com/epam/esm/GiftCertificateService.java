package com.epam.esm;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Pagination;
import com.epam.esm.entity.Tag;
import java.util.List;
import java.util.Map;

/**
 * The interface Certificate service.
 */
public interface GiftCertificateService {
    /**
     * This method is used to create the certificate
     *
     * @param certificate the certificate to be created
     * @return true if certificate was created, false if it were not
     */
    GiftCertificate create(GiftCertificate certificate);

    /**
     * This method is used to delete the certificate by id
     *
     * @param id the id of certificate to be deleted
     * @throws com.epam.esm.exception.EntityNotFoundException if certificate does not exist
     */
    void delete(long id);

    /**
     * This method is used to update the certificate
     *
     * @param giftCertificate the certificate to be updated
     * @return an Optional with the value of updated certificate
     * @throws com.epam.esm.exception.EntityNotFoundException if certificate does not exist
     */
    GiftCertificate update(GiftCertificate giftCertificate, long id);

    /**
     * This method is used to return certificate by id
     *
     * @param id the id of certificate to be returned
     * @return on Optional with the value of find Certificate
     * @throws com.epam.esm.exception.EntityNotFoundException if certificate was not found
     */
    GiftCertificate findById(long id);

    /**
     * This method is used for add tag to certificate
     *
     * @param tagList the list with tags value
     * @param id the id of certificate for binding tags
     */
    GiftCertificate addTagToCertificate(List<Tag> tagList, long id);

    /**
     * This method is used to return the list of certificates
     *
     * @return List of all certificates or empty List if
     * no certificates were found
     */
    List<GiftCertificate> getAll(Pagination pagination);

    /**
     * This method is used to fetch data from certificate list and can
     * sort the list by name asc/desc, date asc/date and find the certificates
     * with tag
     *
     * @param param name tag for searching in certificateList. (can be Null)
     * @param sort the sorting type selection. Possible values: name_asc, name_desc,
     *             date_asc, date_desc, null
     * @param certificateList the list of certificates
     * @return List of sorted certificates, or unsorted List if received sort
     * does not exist(or null) or received certificates is null
     */
    List<GiftCertificate> getFilteredListCertificates(Map<String,String> filterParam, Pagination pagination);
}

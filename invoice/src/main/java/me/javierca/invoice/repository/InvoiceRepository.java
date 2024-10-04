package me.javierca.invoice.repository;

import me.javierca.invoice.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

  @Query("SELECT invoice FROM Invoice invoice WHERE invoice.clientId = ?1")
  List<Invoice> findByClientId(final Long clientId);

  @Modifying
  @Query("DELETE FROM Invoice invoice WHERE invoice.clientId = ?1")
  int deleteByClientId(final Long clientId);

}

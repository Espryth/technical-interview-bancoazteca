package me.javierca.invoice.service;

import lombok.extern.slf4j.Slf4j;
import me.javierca.invoice.client.ClientFeignClient;
import me.javierca.invoice.dto.ClientDTO;
import me.javierca.invoice.entity.Invoice;
import me.javierca.invoice.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
public class InvoiceService {

  private final InvoiceRepository invoiceRepository;
  private final ClientFeignClient clientFeignClient;

  @Autowired
  InvoiceService(final InvoiceRepository invoiceRepository, final ClientFeignClient clientFeignClient) {
    this.invoiceRepository = invoiceRepository;
    this.clientFeignClient = clientFeignClient;
  }

  public ResponseEntity<Invoice> getInvoice(final Long id) {
    log.info("Getting invoice with id: {}", id);
    return this.invoiceRepository.findById(id)
        .map(invoice -> {
          log.info("Invoice found: {}", invoice);
          return ResponseEntity.ok(invoice);
        })
        .orElseGet(() -> {
          log.warn("Invoice with id {} not found", id);
          return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        });
  }

  public ResponseEntity<List<Invoice>> getClientInvoices(final Long clientId) {
    return ResponseEntity.ok(this.invoiceRepository.findByClientId(clientId));
  }

  public ResponseEntity<?> createInvoice(final Invoice invoice) {
    final ClientDTO client;
    try {
      client = this.clientFeignClient.getClient(invoice.getClientId());
    } catch (final Exception e) {
      log.error("Client not found: {}", invoice.getClientId());
      return ResponseEntity.badRequest().body("Error getting client");
    }
    this.invoiceRepository.save(invoice);
    log.info("Client found: {}, creating invoice {}", client, invoice.getId());
    return ResponseEntity.ok(invoice);
  }

  @Transactional
  public ResponseEntity<?> updateInvoice(final Long id, final Invoice invoiceToReplace) {
    log.info("Updating invoice with id: {}", id);
    final var invoice = this.invoiceRepository.findById(id).orElse(null);
    if (invoice == null) {
      log.error("Cannot update invoice with id: {}, because it does not exist", id);
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body("Invoice not found");
    }
    if (invoiceToReplace.getAmount() != null) {
      log.info("Updating amount from {} to {}", invoice.getAmount(), invoiceToReplace.getAmount());
      invoice.setAmount(invoiceToReplace.getAmount());
    }
    if (invoiceToReplace.getDescription() != null) {
      log.info("Updating description from {} to {}", invoice.getDescription(), invoiceToReplace.getDescription());
      invoice.setDescription(invoiceToReplace.getDescription());
    }
    this.invoiceRepository.save(invoice);
    return ResponseEntity.ok(invoice);
  }

  @Transactional
  public ResponseEntity<?> deleteInvoice(final Long id) {
    log.info("Deleting invoice with id: {}", id);
    return this.invoiceRepository.findById(id)
        .map(invoice -> {
          log.info("Invoice deleted: {}", invoice);
          this.invoiceRepository.delete(invoice);
          return ResponseEntity.ok(invoice);
        })
        .orElseGet(() -> {
          log.error("Cannot delete invoice with id: {}, because it does not exist", id);
          return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        });
  }

  @Transactional
  public ResponseEntity<Integer> deleteClientInvoices(final Long clientId) {
    return ResponseEntity.ok(this.invoiceRepository.deleteByClientId(clientId));
  }
}

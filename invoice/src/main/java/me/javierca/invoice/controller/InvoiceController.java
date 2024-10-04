package me.javierca.invoice.controller;

import me.javierca.invoice.entity.Invoice;
import me.javierca.invoice.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/invoices")
public class InvoiceController {

  private final InvoiceService invoiceService;

  @Autowired
  InvoiceController(final InvoiceService invoiceService) {
    this.invoiceService = invoiceService;
  }

  @PostMapping("/create")
  public ResponseEntity<?> createInvoice(final @Valid @RequestBody Invoice invoice) {
    return this.invoiceService.createInvoice(invoice);
  }

  @PutMapping("/update/{id}")
  public ResponseEntity<?> updateInvoice(final @PathVariable Long id, final @RequestBody Invoice invoice) {
    return this.invoiceService.updateInvoice(id, invoice);
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<?> deleteInvoice(final @PathVariable Long id) {
    return this.invoiceService.deleteInvoice(id);
  }

  @DeleteMapping("/delete/client/{clientId}")
  public ResponseEntity<?> deleteClientInvoices(final @PathVariable Long clientId) {
    return this.invoiceService.deleteClientInvoices(clientId);
  }

  @GetMapping("/get/{id}")
  public ResponseEntity<Invoice> getInvoice(final @PathVariable Long id) {
    return this.invoiceService.getInvoice(id);
  }

  @GetMapping("/get/client/{clientId}")
  public ResponseEntity<List<Invoice>> getClientInvoices(final @PathVariable Long clientId) {
    return this.invoiceService.getClientInvoices(clientId);
  }

}

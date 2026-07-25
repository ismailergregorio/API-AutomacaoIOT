package com.example.AutomacaoIOT.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.AutomacaoIOT.Service.FileStorageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/upload")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ControllersUpload {

 private final FileStorageService service;

 @PostMapping
 public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) {
  String nome = service.salvarArquivo(file);
  return ResponseEntity.ok("Arquivo salvo: " + nome);
 }
}
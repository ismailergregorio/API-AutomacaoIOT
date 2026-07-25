package com.example.AutomacaoIOT.Controllers;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.example.AutomacaoIOT.Config.FileStorageConfig;
import com.example.AutomacaoIOT.DTO.Files.FileDTO;
import com.example.AutomacaoIOT.Service.FileStorageService;

import lombok.RequiredArgsConstructor;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/files")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class ControllersFile {

  private final FileStorageService service;

  private final FileStorageConfig config;

  @GetMapping("/download/{nomeArquivo}")
  public ResponseEntity<Resource> download(@PathVariable String nomeArquivo) {
    try {
      Path caminho = Paths.get(config.getUploadDir()).resolve(nomeArquivo);
      Resource resource = new UrlResource(caminho.toUri());

      if (!resource.exists()) {
        return ResponseEntity.notFound().build();
      }

      return ResponseEntity.ok()
          .header(HttpHeaders.CONTENT_DISPOSITION,
              "attachment; filename=\"" + resource.getFilename() + "\"")
          .contentType(MediaType.APPLICATION_OCTET_STREAM)
          .body(resource);

    } catch (Exception e) {
      throw new RuntimeException("Erro ao baixar arquivo", e);
    }
  }

  @GetMapping
  public List<FileDTO> listarArquivos() {
    return service.listarArquivos();
  }

  @DeleteMapping("/{nomeArquivo}")
  public ResponseEntity<?> deletarArquivo(@PathVariable String nomeArquivo) {
    try {
      Path caminho = Paths.get(config.getUploadDir()).resolve(nomeArquivo);
      Resource resource = new UrlResource(caminho.toUri());

      if (!resource.exists()) {
        return ResponseEntity.notFound().build();
      }

      Files.delete(caminho);
      return ResponseEntity.status(HttpStatus.OK).body("Arquivo deletado");

    } catch (Exception e) {
      throw new RuntimeException("Erro ao baixar arquivo", e);
    }
  }
}
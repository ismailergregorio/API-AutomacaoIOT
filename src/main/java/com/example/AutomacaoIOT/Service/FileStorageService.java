package com.example.AutomacaoIOT.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.AutomacaoIOT.Config.FileStorageConfig;
import com.example.AutomacaoIOT.DTO.Files.FileDTO;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class FileStorageService {

 private final FileStorageConfig config;

 public String salvarArquivo(MultipartFile file) {
  try {
   Path pasta = Paths.get(config.getUploadDir());

   if (!Files.exists(pasta)) {
    Files.createDirectories(pasta);
   }

   String nomeArquivo = file.getOriginalFilename();

   Path caminho = pasta.resolve(nomeArquivo);

   Files.copy(file.getInputStream(), caminho, StandardCopyOption.REPLACE_EXISTING);

   return nomeArquivo;

  } catch (IOException e) {
   throw new RuntimeException("Erro ao salvar arquivo", e);
  }
 }

 public List<FileDTO> listarArquivos() {
  try {
   Path pasta = Paths.get(config.getUploadDir());

   if (!Files.exists(pasta)) {
    return List.of();
   }

   try (Stream<Path> arquivos = Files.list(pasta)) {
    return arquivos
      .filter(Files::isRegularFile)
      .map(path -> {
       try {
        return new FileDTO(
          path.getFileName().toString(),
          Files.size(path),
          Files.getLastModifiedTime(path).toMillis());
       } catch (IOException e) {
        throw new RuntimeException(e);
       }
      })
      .collect(Collectors.toList());
   }

  } catch (IOException e) {
   throw new RuntimeException("Erro ao listar arquivos", e);
  }
 }
}
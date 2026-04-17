package br.com.jhonecmd.courses_api.modules.category.courses.dto;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateCourseDTO {

    @Schema(example = "Sistema de Informação")
    @NotBlank(message = "Name is required.")
    private String name;

    @Schema(example = "O curso de bacharelado em Sistemas de Informação tem duração de 4 anos e forma profissionais para desenvolver, implementar e gerenciar tecnologias em empresas. Combina áreas de computação (programação, banco de dados) com gestão de negócios. Com alta empregabilidade, o aluno sai capacitado para atuar como desenvolvedor, analista de sistemas ou gestor de TI.")
    private String description;
    private Boolean active;

    @Schema(example = "Jose Doe")
    @NotBlank(message = "teacher is required.")
    private String teacherName;

    private UUID categoryId;
}

package org.melekhov.deal.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.melekhov.deal.model.Statement;
import org.melekhov.deal.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/deal/admin/statement")
@Slf4j
@Tag(name = "Admin Statement API", description = "API для работы с заявками (Statement) в административных целях")
public class AdminController {

    private final AdminService adminService;

    @Operation(
            summary = "Получить заявку по ID",
            description = "Возвращает полную информацию о заявке (Statement) по указанному UUID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное выполнение запроса",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Statement.class))),
            @ApiResponse(responseCode = "404", description = "Заявка с указанным ID не найдена")
    })
    @GetMapping("/{statementId}")
    public ResponseEntity<Statement> getStatementWithId(@PathVariable UUID statementId) {
        Statement statement = adminService.getStatementWithId(statementId);
        log.info("Response statement={}", statement);
        return ResponseEntity.ok().body(statement);
    }

    @Operation(
            summary = "Получить список всех заявок",
            description = "Возвращает список всех заявок (Statement), доступных в системе."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное выполнение запроса",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Statement.class))))
    })
    @GetMapping()
    public ResponseEntity<List<Statement>> getListAllStatements() {
        List<Statement> statementList = adminService.getAllStatements();
        return ResponseEntity.ok().body(statementList);
    }
}


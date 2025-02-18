package com.api.task_manager.configurations

import io.swagger.v3.core.converter.AnnotatedType
import io.swagger.v3.core.converter.ModelConverters
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.Operation
import io.swagger.v3.oas.models.PathItem
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.media.Content
import io.swagger.v3.oas.models.media.MediaType
import io.swagger.v3.oas.models.responses.ApiResponse
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springdoc.core.customizers.OpenApiCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.function.Consumer


@Configuration
class SwaggerConfiguration {
    @Bean
    fun customOpenApi(): OpenAPI {
        val securitySchemeName = "bearerAuth"
        return OpenAPI()
            .info(Info().title("Celta Tasks API").version("v2.0.0"))
            .components(
                Components()
                    .addSecuritySchemes(
                        securitySchemeName,
                        SecurityScheme()
                            .name(securitySchemeName)
                            .type(SecurityScheme.Type.HTTP)
                            .scheme("bearer")
                            .bearerFormat("JWT")
                    )
            )
            .addSecurityItem(SecurityRequirement().addList(securitySchemeName))
    }

    @Bean
    fun openApiCustomizer(): OpenApiCustomizer {
        data class ErrorResponse(
            val timestamp: String,
            val status: Int,
            val error: String,
            val message: String?,
            val path: String?
        )

        val resolvedSchema = ModelConverters.getInstance()
            .resolveAsResolvedSchema(AnnotatedType(ErrorResponse::class.java))

        val content = Content().apply {
            addMediaType(
                "application/json",
                MediaType().schema(resolvedSchema.schema)
            )
        }
        return OpenApiCustomizer { openApi: OpenAPI? ->
            openApi!!
                .paths
                .values
                .forEach(
                    Consumer { pathItem: PathItem? ->
                        pathItem!!
                            .readOperations()
                            .forEach(
                                Consumer { operation: Operation? ->
                                    operation!!
                                        .responses
                                        .addApiResponse(
                                            "400",
                                            ApiResponse()
                                                .description("Bad Request")
                                                .content(content)
                                        )
                                        .addApiResponse(
                                            "404",
                                            ApiResponse()
                                                .description("Resource Not Found")
                                                .content(content)
                                        )
                                })
                    })
        }
    }
}


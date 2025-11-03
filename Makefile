.PHONY: help build test clean localstack-up localstack-down sam-build sam-invoke sam-start-api sam-deploy native-compile all

help: ## Mostrar esta ayuda
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-20s\033[0m %s\n", $$1, $$2}'

build: ## Compilar el proyecto
	./gradlew clean build

test: ## Ejecutar todas las pruebas
	./gradlew test

clean: ## Limpiar archivos de compilación
	./gradlew clean
	rm -rf .aws-sam
	rm -rf .localstack

localstack-up: ## Iniciar LocalStack con Docker Compose
	docker-compose up -d localstack
	@echo "Esperando a que LocalStack esté listo..."
	@sleep 5
	@echo "LocalStack debería estar disponible en http://localhost:4566"

localstack-down: ## Detener LocalStack
	docker-compose down

localstack-logs: ## Ver logs de LocalStack
	docker-compose logs -f localstack

sam-build: ## Construir aplicación SAM
	cd lambda-infra && sam build --template template.yaml

sam-invoke: ## Invocar función Lambda localmente con evento hello.json
	cd lambda-infra && sam local invoke "ReactiveFunction" --event events/hello.json

sam-start-api: ## Iniciar API Gateway local con SAM
	cd lambda-infra && sam local start-api

sam-deploy: ## Desplegar a AWS (modo guiado)
	cd lambda-infra && sam deploy --guided

native-compile: ## Compilar imagen nativa con GraalVM
	./gradlew :lambda-core:nativeCompile

all: clean build test ## Limpiar, compilar y ejecutar pruebas

.DEFAULT_GOAL := help






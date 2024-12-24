# Etapa 1: Construção
FROM maven:3.8.5-openjdk-17-slim AS build

# Definir o diretório de trabalho dentro do container
WORKDIR /app

# Copiar o pom do projeto para dentro do diretório app
COPY pom.xml .

# Baixar as dependências do Maven
RUN mvn dependency:go-offline

# Copiar a pasta src do projeto para a pasta src do container
COPY src /app/src

# Compilar e empacotar o projeto sem a necessidade de executar os testes
RUN mvn clean package -DskipTests

# Etapa 2: Imagem final
FROM openjdk:17-jdk-slim

# Definir o diretório de trabalho dentro do container
WORKDIR /app

# Copiar o arquivo JAR e copiar para o diretório raiz da nova imagem
COPY --from=build /app/target/*.jar app.jar

# Expor a porta onde a API estará escutando
EXPOSE 8080

# Usar ENTRYPOINT para garantir que o comando seja executado com a imagem
ENTRYPOINT ["java", "-jar", "/app/app.jar"]

# Definir o perfil ativo do Spring Boot como "dev"
CMD ["--spring.profiles.active=dev"]
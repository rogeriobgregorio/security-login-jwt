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
RUN mvn clean package -DskipTests && \
    # Limpar cache do Maven para reduzir o tamanho da imagem final
    rm -rf ~/.m2/repository/* && \
    # Remover arquivos JAR desnecessários (como sources e javadocs)
    rm -rf /app/target/*-sources.jar /app/target/*-javadoc.jar /app/target/*.test-jar

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

# Definir o perfil ativo do Spring Boot como "dev" (pode ser sobreposto em tempo de execução)
CMD ["--spring.profiles.active=${SPRING_PROFILE:-dev}"]
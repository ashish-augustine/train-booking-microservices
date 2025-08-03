FROM eclipse-temurin:11-jdk
WORKDIR /app

# Copiar el script desde la carpeta scripts en el proyecto raíz
COPY ./scripts/wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh

# Copiar el JAR del microservicio
COPY payment-service/target/*.jar app.jar

# Espera a MySQL y Eureka antes de arrancar la app
ENTRYPOINT ["/wait-for-it.sh", "mysql:3306", "--", "/wait-for-it.sh", "eureka-server:8761", "--", "java", "-jar", "app.jar"]
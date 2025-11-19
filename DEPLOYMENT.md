# Reto 2: Aplicación backend de alta disponibilidad

## Descripción del Proyecto

Este proyecto implementa una arquitectura de microservicios con alta disponibilidad utilizando **Spring Boot**, **Docker** y **Kubernetes**.

## Arquitectura

```
┌─────────────────────────────────────────────┐
│           Kubernetes Cluster                │
│                                             │
│  ┌────────────────────────────────────┐    │
│  │   Frontend Service (NodePort)      │    │
│  │   Port: 30080                      │    │
│  └──────────┬─────────────────────────┘    │
│             │                               │
│  ┌──────────▼──────────────────────────┐   │
│  │  Frontend Deployment (3 replicas)   │   │
│  │  - Pod Frontend 1                   │   │
│  │  - Pod Frontend 2                   │   │
│  │  - Pod Frontend 3                   │   │
│  └──────────┬──────────────────────────┘   │
│             │                               │
│             │ HTTP Request                  │
│             │                               │
│  ┌──────────▼──────────────────────────┐   │
│  │  Backend Service (ClusterIP)        │   │
│  │  Port: 8080                         │   │
│  └──────────┬──────────────────────────┘   │
│             │                               │
│  ┌──────────▼──────────────────────────┐   │
│  │  Backend Deployment (2 replicas)    │   │
│  │  - Pod Backend 1                    │   │
│  │  - Pod Backend 2                    │   │
│  └─────────────────────────────────────┘   │
│                                             │
└─────────────────────────────────────────────┘
```

## Componentes

### 1. Backend Service
- **Tecnología**: Spring Boot 3.2.0 + Java 17
- **Puerto**: 8080
- **Endpoint**: `/api/products`
- **Réplicas**: 2
- **Tipo de Service**: ClusterIP (acceso interno)
- **Funcionalidad**: API REST que retorna lista de productos

### 2. Frontend Service
- **Tecnología**: Spring Boot 3.2.0 + Java 17
- **Puerto**: 8080 (NodePort: 30080)
- **Réplicas**: 3
- **Tipo de Service**: NodePort (acceso externo)
- **Funcionalidad**: Sirve contenido estático (HTML, CSS, JS) y consume el API del backend

## Estructura del Proyecto

```
RetoTecnico2/
├── backend-service/
│   ├── src/
│   │   └── main/
│   │       ├── java/com/retotecnico/backend/
│   │       │   ├── BackendServiceApplication.java
│   │       │   ├── controller/
│   │       │   │   └── ProductController.java
│   │       │   └── model/
│   │       │       └── Product.java
│   │       └── resources/
│   │           └── application.properties
│   ├── Dockerfile
│   └── pom.xml
│
├── frontend-service/
│   ├── src/
│   │   └── main/
│   │       ├── java/com/retotecnico/frontend/
│   │       │   └── FrontendServiceApplication.java
│   │       └── resources/
│   │           ├── application.properties
│   │           └── static/
│   │               ├── index.html
│   │               ├── styles.css
│   │               └── app.js
│   ├── Dockerfile
│   └── pom.xml
│
└── k8s/
    ├── backend-deployment.yaml
    ├── backend-service.yaml
    ├── frontend-deployment.yaml
    └── frontend-service.yaml
```

## Requisitos Previos

- Java 17+
- Maven 3.6+
- Docker
- Kubernetes 
- kubectl

## Instalación y Despliegue

### Paso 1: Construir las imágenes Docker

```powershell
# Backend Service
cd backend-service
docker build -t backend-service:1.0.0 .

# Frontend Service
cd ../frontend-service
docker build -t frontend-service:1.0.0 .
```

### Paso 2: Cargar imágenes en Minikube (si usas Minikube)

```powershell
# Si usas Minikube, carga las imágenes
minikube image load backend-service:1.0.0
minikube image load frontend-service:1.0.0
```

### Paso 3: Desplegar en Kubernetes

```powershell
# Aplicar manifiestos del backend
kubectl apply -f k8s/backend-deployment.yaml
kubectl apply -f k8s/backend-service.yaml

# Aplicar manifiestos del frontend
kubectl apply -f k8s/frontend-deployment.yaml
kubectl apply -f k8s/frontend-service.yaml
```

### Paso 4: Verificar el despliegue

```powershell
# Ver todos los pods
kubectl get pods

# Ver los services
kubectl get services

# Ver los deployments
kubectl get deployments
```

### Paso 5: Acceder a la aplicación

#### Si usas Minikube:
```powershell
minikube service frontend-service --url
```

#### Si usas Docker Desktop o cluster local:
```
http://localhost:30080
```

## Comandos Útiles

### Verificar logs de los pods
```powershell
# Backend
kubectl logs -l app=backend

# Frontend
kubectl logs -l app=frontend
```

### Ver detalles de un deployment
```powershell
kubectl describe deployment backend-deployment
kubectl describe deployment frontend-deployment
```

### Escalar réplicas
```powershell
# Escalar backend a 3 réplicas
kubectl scale deployment backend-deployment --replicas=3

# Escalar frontend a 5 réplicas
kubectl scale deployment frontend-deployment --replicas=5
```

### Eliminar todos los recursos
```powershell
kubectl delete -f k8s/
```

## API Endpoints

### Backend Service

**GET** `/api/products`

Respuesta:
```json
[
  {
    "productId": 1,
    "title": "Title 01"
  },
  {
    "productId": 2,
    "title": "Title 02"
  }
]
```

## Características Implementadas

✅ **Alta Disponibilidad**: Múltiples réplicas de cada servicio  
✅ **Balanceo de Carga**: Kubernetes distribuye automáticamente las peticiones  
✅ **Health Checks**: Liveness y Readiness probes configurados  
✅ **Límites de Recursos**: CPU y memoria configurados  
✅ **Contenerización**: Dockerfiles multi-stage para optimizar el tamaño  
✅ **Arquitectura de Microservicios**: Separación clara de responsabilidades  

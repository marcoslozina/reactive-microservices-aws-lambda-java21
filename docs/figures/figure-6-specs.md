# Figura 6: Especificaciones de Diseño

## Thread-per-Request vs Event Loop

### Especificaciones Técnicas

- **Formato:** PNG
- **Ancho:** 2400px
- **Fondo:** #F5F7FA (gris claro)
- **Color de texto:** #1A1F21 (gris oscuro)
- **Color de bordes:** #D0D4D8 (gris medio)
- **Color de líneas conectoras:** #00AEEF (cyan)

### Diseño

#### Dos columnas lado a lado

**Columna Izquierda: Thread-per-Request (Blocking)**
- Título: "Thread-per-request (Blocking)"
- Cuatro rectángulos blancos apilados con bordes redondeados (#D0D4D8)
- Etiquetas:
  - Thread 1
  - Thread 2
  - Thread 3
  - Thread 4
- Línea vertical cyan corta (#00AEEF) en el borde izquierdo de cada rectángulo para indicar "un thread por request"

**Columna Derecha: Event Loop (Non-blocking)**
- Título: "Event Loop (Non-blocking)"
- Óvalo central etiquetado "Event Loop" en color #1A1F21
- Cuatro círculos pequeños alrededor del óvalo, cada uno etiquetado "Task"
- Líneas cyan rectas (#00AEEF) conectando cada círculo al óvalo
- Líneas formando un patrón "+" (arriba, abajo, izquierda, derecha)
- Líneas espaciadas uniformemente

### Reglas de Espaciado

- Mínimo 40px de padding entre límites de formas
- Las líneas deben conectar a los *bordes* de las formas, nunca cruzando texto
- Sin líneas diagonales
- Sin líneas cruzando texto

### Guía de Implementación

1. Crear fondo #F5F7FA
2. Dividir en dos columnas iguales
3. **Columna izquierda:**
   - Agregar título "Thread-per-request (Blocking)"
   - Crear 4 rectángulos blancos apilados con bordes redondeados
   - Etiquetar cada uno: Thread 1, Thread 2, Thread 3, Thread 4
   - Agregar línea vertical cyan delgada en el borde izquierdo de cada rectángulo
4. **Columna derecha:**
   - Agregar título "Event Loop (Non-blocking)"
   - Crear óvalo central etiquetado "Event Loop"
   - Crear 4 círculos pequeños alrededor del óvalo
   - Etiquetar cada círculo "Task"
   - Conectar cada círculo al óvalo con líneas cyan rectas
   - Espaciar líneas uniformemente (arriba, abajo, izquierda, derecha)
5. Verificar que todas las líneas conecten a bordes, no crucen texto
6. Exportar como PNG a 2400px de ancho



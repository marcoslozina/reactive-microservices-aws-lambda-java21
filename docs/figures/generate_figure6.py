#!/usr/bin/env python3
"""
Script para generar la Figura 6: Thread-per-Request vs Event Loop

Requisitos:
    pip install Pillow

Ejecutar:
    python generate_figure6.py
"""

from PIL import Image, ImageDraw, ImageFont
import os

# Colores según especificaciones
BACKGROUND_COLOR = "#F5F7FA"
TEXT_COLOR = "#1A1F21"
BORDER_COLOR = "#D0D4D8"
LINE_COLOR = "#00AEEF"
WHITE = "#FFFFFF"

# Dimensiones
WIDTH = 2400
HEIGHT = 1200
PADDING = 40
COLUMN_WIDTH = (WIDTH - 3 * PADDING) // 2

# Crear imagen
img = Image.new('RGB', (WIDTH, HEIGHT), BACKGROUND_COLOR)
draw = ImageDraw.Draw(img)

# Intentar cargar fuente, usar default si no está disponible
try:
    font_title = ImageFont.truetype("/usr/share/fonts/truetype/dejavu/DejaVuSans-Bold.ttf", 48)
    font_text = ImageFont.truetype("/usr/share/fonts/truetype/dejavu/DejaVuSans.ttf", 36)
except:
    try:
        font_title = ImageFont.truetype("arial.ttf", 48)
        font_text = ImageFont.truetype("arial.ttf", 36)
    except:
        font_title = ImageFont.load_default()
        font_text = ImageFont.load_default()

# ===== COLUMNA IZQUIERDA: Thread-per-Request =====
left_x = PADDING
left_y = PADDING + 80  # Espacio para título

# Título
draw.text((left_x, PADDING), "Thread-per-request (Blocking)", 
          fill=TEXT_COLOR, font=font_title)

# Rectángulos para threads
thread_height = 120
thread_spacing = 20
thread_width = COLUMN_WIDTH - 40
thread_y = left_y

for i in range(1, 5):
    # Rectángulo con bordes redondeados
    rect_x1 = left_x + 20
    rect_y1 = thread_y
    rect_x2 = rect_x1 + thread_width
    rect_y2 = rect_y1 + thread_height
    
    # Dibujar rectángulo con bordes redondeados
    draw.rounded_rectangle([rect_x1, rect_y1, rect_x2, rect_y2], 
                           radius=10, fill=WHITE, outline=BORDER_COLOR, width=2)
    
    # Línea vertical cyan en el borde izquierdo
    line_x = rect_x1 + 5
    draw.line([(line_x, rect_y1 + 10), (line_x, rect_y2 - 10)], 
              fill=LINE_COLOR, width=4)
    
    # Texto "Thread N"
    text_x = rect_x1 + 30
    text_y = rect_y1 + (thread_height - 36) // 2
    draw.text((text_x, text_y), f"Thread {i}", 
              fill=TEXT_COLOR, font=font_text)
    
    thread_y += thread_height + thread_spacing

# ===== COLUMNA DERECHA: Event Loop =====
right_x = WIDTH // 2 + PADDING // 2
right_y = PADDING + 80  # Espacio para título

# Título
draw.text((right_x, PADDING), "Event Loop (Non-blocking)", 
          fill=TEXT_COLOR, font=font_title)

# Centro del event loop
center_x = right_x + COLUMN_WIDTH // 2
center_y = right_y + 300

# Óvalo del Event Loop
ellipse_width = 200
ellipse_height = 120
ellipse_x1 = center_x - ellipse_width // 2
ellipse_y1 = center_y - ellipse_height // 2
ellipse_x2 = center_x + ellipse_width // 2
ellipse_y2 = center_y + ellipse_height // 2

draw.ellipse([ellipse_x1, ellipse_y1, ellipse_x2, ellipse_y2], 
             fill=WHITE, outline=BORDER_COLOR, width=2)

# Texto "Event Loop" en el óvalo
text_bbox = draw.textbbox((0, 0), "Event Loop", font=font_text)
text_w = text_bbox[2] - text_bbox[0]
text_h = text_bbox[3] - text_bbox[1]
text_x = center_x - text_w // 2
text_y = center_y - text_h // 2
draw.text((text_x, text_y), "Event Loop", fill=TEXT_COLOR, font=font_text)

# Cuatro círculos "Task" alrededor del óvalo
task_radius = 50
task_distance = 180  # Distancia desde el centro del óvalo

# Posiciones: arriba, abajo, izquierda, derecha
task_positions = [
    (center_x, center_y - task_distance),  # Arriba
    (center_x, center_y + task_distance),  # Abajo
    (center_x - task_distance, center_y),   # Izquierda
    (center_x + task_distance, center_y),   # Derecha
]

for i, (task_x, task_y) in enumerate(task_positions):
    # Círculo Task
    circle_x1 = task_x - task_radius
    circle_y1 = task_y - task_radius
    circle_x2 = task_x + task_radius
    circle_y2 = task_y + task_radius
    
    draw.ellipse([circle_x1, circle_y1, circle_x2, circle_y2], 
                 fill=WHITE, outline=BORDER_COLOR, width=2)
    
    # Texto "Task"
    text_bbox = draw.textbbox((0, 0), "Task", font=font_text)
    text_w = text_bbox[2] - text_bbox[0]
    text_h = text_bbox[3] - text_bbox[1]
    text_x = task_x - text_w // 2
    text_y = task_y - text_h // 2
    draw.text((text_x, text_y), "Task", fill=TEXT_COLOR, font=font_text)
    
    # Línea cyan conectando al óvalo
    # Calcular punto de conexión en el borde del óvalo
    if i == 0:  # Arriba
        oval_conn_x = center_x
        oval_conn_y = ellipse_y1
        task_conn_x = task_x
        task_conn_y = circle_y2
    elif i == 1:  # Abajo
        oval_conn_x = center_x
        oval_conn_y = ellipse_y2
        task_conn_x = task_x
        task_conn_y = circle_y1
    elif i == 2:  # Izquierda
        oval_conn_x = ellipse_x1
        oval_conn_y = center_y
        task_conn_x = circle_x2
        task_conn_y = task_y
    else:  # Derecha
        oval_conn_x = ellipse_x2
        oval_conn_y = center_y
        task_conn_x = circle_x1
        task_conn_y = task_y
    
    # Dibujar línea recta
    draw.line([(oval_conn_x, oval_conn_y), (task_conn_x, task_conn_y)], 
              fill=LINE_COLOR, width=3)

# Guardar imagen
output_path = os.path.join(os.path.dirname(__file__), "figure-6-thread-vs-eventloop.png")
img.save(output_path, "PNG")
print(f"✅ Figura 6 generada exitosamente: {output_path}")
print(f"   Dimensiones: {WIDTH}x{HEIGHT}px")



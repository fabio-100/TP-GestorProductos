// Importamos los componentes principales de Swing.
import javax.swing.*;

// Importamos DefaultTableModel para administrar los datos de la JTable.
import javax.swing.table.DefaultTableModel;

// importo TableRowSorter para ordenar las filas de una tabla. FFF
import javax.swing.table.TableRowSorter;

// Importamos clases para organizar los componentes gráficos.
import java.awt.*;


// ============================================================
// CLASE PRINCIPAL
// ============================================================

// GestorProductos hereda de JFrame.
// Por lo tanto, esta clase representa una ventana.
public class GestorProductos extends JFrame {

    // ========================================================
    // COMPONENTES DEL FORMULARIO
    // ========================================================

    // Campo donde el usuario escribe el nombre.
    private JTextField txtNombre;

    // Campo donde el usuario escribe el precio.
    private JTextField txtPrecio;

    // Campo donde el usuario escribe el stock.
    private JTextField txtStock;

    // Lista desplegable para seleccionar la categoría.
    private JComboBox<String> cmbCategoria;


    // ========================================================
    // COMPONENTES DE LA TABLA
    // ========================================================

    // JTable muestra los productos al usuario.
    private JTable tabla;

    // DefaultTableModel administra las filas y columnas.
    private DefaultTableModel modelo;


    // ========================================================
    // COMPONENTE PARA MOSTRAR EL TOTAL
    // ========================================================

    // JLabel utilizado para mostrar el valor total del stock.
    private int filaModificar = -1;

    private JLabel lblTotal;


    // ========================================================
    // CONSTRUCTOR
    // ========================================================

    public GestorProductos() {

        // Título que aparecerá en la ventana.
        setTitle("Gestor de Productos");

        // Tamaño de la ventana: ancho x alto.
        setSize(800, 500);

        // Al presionar X se cierra el programa.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Centra la ventana en la pantalla.
        setLocationRelativeTo(null);

        // Llamamos al método que construye la interfaz.
        crearInterfaz();
    }


    // ========================================================
    // CREAR INTERFAZ
    // ========================================================

    private void crearInterfaz() {

        // Creamos un panel con 5 filas y 2 columnas.
        JPanel panelFormulario =
                new JPanel(new GridLayout(6, 2, 10, 10));

        // Agregamos un margen interno al formulario.
        panelFormulario.setBorder(
                BorderFactory.createEmptyBorder(
                        15, 15, 15, 15
                )
        );


        // ----------------------------------------------------
        // CAMPO NOMBRE
        // ----------------------------------------------------

        // Agregamos la etiqueta.
        JLabel lblNombre = new JLabel("Nombre:"); //Creo un JLabel con la etiqueta "Nombre". FFF
        lblNombre.setHorizontalAlignment(SwingConstants.CENTER); //Lo centralizo horizontalmente. FFF
        panelFormulario.add(lblNombre); //Lo agrego al formulario. FFF

        // Creamos el campo de texto.
        txtNombre = new JTextField();

        // Agregamos el campo al formulario.
        panelFormulario.add(txtNombre);


        // ----------------------------------------------------
        // CAMPO PRECIO
        // ----------------------------------------------------

        JLabel lblPrecio = new JLabel("Precio:");
        lblPrecio.setHorizontalAlignment(SwingConstants.CENTER);
        panelFormulario.add(lblPrecio);

        txtPrecio = new JTextField();

        panelFormulario.add(txtPrecio);


        // ----------------------------------------------------
        // CAMPO STOCK
        // ----------------------------------------------------

        JLabel lblStock = new JLabel("Stock:");
        lblStock.setHorizontalAlignment(SwingConstants.CENTER);
        panelFormulario.add(lblStock);

        txtStock = new JTextField();

        panelFormulario.add(txtStock);


        // ----------------------------------------------------
        // CATEGORÍA
        // ----------------------------------------------------

        JLabel lblCategoria = new JLabel("Categoría:");
        lblCategoria.setHorizontalAlignment(SwingConstants.CENTER);
        panelFormulario.add(lblCategoria);

        // Creamos el ComboBox.
        cmbCategoria = new JComboBox<>();

        // Agregamos las opciones.
        cmbCategoria.addItem("Almacén");
        cmbCategoria.addItem("Bebidas");
        cmbCategoria.addItem("Limpieza");
        cmbCategoria.addItem("Verduleria");
        cmbCategoria.addItem("Lácteos");
        cmbCategoria.addItem("Electrodomesticos");
        cmbCategoria.addItem("Otros");

        // Agregamos el ComboBox.
        panelFormulario.add(cmbCategoria);


        // ----------------------------------------------------
        // BOTONES
        // ----------------------------------------------------

        JButton btnAgregar = new JButton("Agregar");
        JButton btnLimpiar = new JButton("Limpiar");
        JButton btnModificar = new JButton("Modificar");

        panelFormulario.add(btnAgregar);
        panelFormulario.add(btnLimpiar);
        panelFormulario.add(btnModificar);


        // ====================================================
        // CREAR TABLA
        // ====================================================

        // Nombres de las columnas.
        String[] columnas = {
                "Nombre",
                "Precio",
                "Stock",
                "Categoría",
                "Valor Stock"
        };

        // Creamos el modelo sin filas inicialmente.
        modelo = new DefaultTableModel(columnas, 0);

        // Creamos la tabla utilizando nuestro modelo.
        tabla = new JTable(modelo);

        // Creo el ordenador/filtro basado en el modelo antes creado. FFF
        TableRowSorter<DefaultTableModel> sorter =
        new TableRowSorter<>(modelo);

        sorter.setSortable(0, true);   //nombre
        sorter.setSortable(1, true);   //precio
        sorter.setSortable(2, true);  //stock
        sorter.setSortable(3, true);  //categoría
        sorter.setSortable(4, false);  //valor total stock

        tabla.setRowSorter(sorter);

        // JScrollPane permite desplazarnos si hay muchas filas.
        JScrollPane scrollTabla =
                new JScrollPane(tabla);


        // ====================================================
        // BOTÓN ELIMINAR
        // ====================================================

        JButton btnEliminar = new JButton("Eliminar");


        // ====================================================
        // ETIQUETA TOTAL
        // ====================================================

        lblTotal =
                new JLabel("Valor total del stock: $0.00");


        // ====================================================
        // EVENTO AGREGAR
        // ====================================================

        // addActionListener detecta el clic del botón.
        btnAgregar.addActionListener(e -> {

            // Ejecutamos nuestro método.
            agregarProducto();
        });


        // ====================================================
        // EVENTO LIMPIAR
        // ====================================================

        btnLimpiar.addActionListener(e -> {

            // Limpiamos los campos.
            limpiarFormulario();
        });


        // ====================================================
        // EVENTO ELIMINAR
        // ====================================================

        btnEliminar.addActionListener(e -> {

            // Eliminamos el producto seleccionado.
            eliminarProducto();
        });
        btnModificar.addActionListener(e -> modificarProducto());

        // ====================================================
        // PANEL INFERIOR
        // ====================================================

        JPanel panelInferior =
                new JPanel(new BorderLayout());

        // Botón a la izquierda.
        panelInferior.add(
                btnEliminar,
                BorderLayout.WEST
        );

        // Total a la derecha.
        panelInferior.add(
                lblTotal,
                BorderLayout.EAST
        );


        // ====================================================
        // CONFIGURAR VENTANA
        // ====================================================

        // Utilizamos BorderLayout para la ventana.
        setLayout(new BorderLayout());

        // Formulario arriba.
        add(
                panelFormulario,
                BorderLayout.NORTH
        );

        // Tabla en el centro.
        add(
                scrollTabla,
                BorderLayout.CENTER
        );

        // Panel inferior abajo.
        add(
                panelInferior,
                BorderLayout.SOUTH
        );
    }


    // ========================================================
    // AGREGAR PRODUCTO
    // ========================================================

    private void agregarProducto() {

        // Obtenemos el nombre escrito.
        // trim() elimina espacios al principio y al final.
        String nombre =
                txtNombre.getText().trim();

        // Obtenemos el precio como texto.
        String precioTexto =
                txtPrecio.getText().trim();

        // Obtenemos el stock como texto.
        String stockTexto =
                txtStock.getText().trim();

        // Obtenemos la categoría seleccionada.
        String categoria =
                cmbCategoria.getSelectedItem().toString();


        // ====================================================
        // VALIDAR NOMBRE
        // ====================================================

        if (nombre.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe ingresar el nombre del producto.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            // Volvemos el cursor al campo Nombre.
            txtNombre.requestFocus();

            // Interrumpimos el método.
            return;
        }


        // Variables donde almacenaremos
        // los valores convertidos.
        double precio;
        int stock;


        // ====================================================
        // CONVERTIR PRECIO
        // ====================================================

        try {

            // Convertimos String a double.
            precio =
                    Double.parseDouble(precioTexto);

        } catch (NumberFormatException e) {

            // Si la conversión falla, mostramos un error.
            JOptionPane.showMessageDialog(
                    this,
                    "El precio debe ser un número válido.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            txtPrecio.requestFocus();

            return;
        }


        // ====================================================
        // CONVERTIR STOCK
        // ====================================================

        try {

            // Convertimos String a int.
            stock =
                    Integer.parseInt(stockTexto);

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "El stock debe ser un número entero.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            txtStock.requestFocus();

            return;
        }


        // ====================================================
        // VALIDAR PRECIO
        // ====================================================

        if (precio <= 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "El precio debe ser mayor que cero.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }


        // ====================================================
        // VALIDAR STOCK
        // ====================================================

        if (stock < 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "El stock no puede ser negativo.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }


        // ====================================================
        // CREAR OBJETO
        // ====================================================

        // Creamos un objeto Producto utilizando
        // los datos ingresados por el usuario.
        Producto producto =
                new Producto(
                        nombre,
                        precio,
                        stock,
                        categoria
                );


        // ====================================================
        // AGREGAR A LA TABLA
        // ====================================================

        // addRow agrega una nueva fila.
        modelo.addRow(new Object[] {

                // Columna 1.
                producto.getNombre(),

                // Columna 2.
                producto.getPrecio(),

                // Columna 3.
                producto.getStock(),

                // Columna 4.
                producto.getCategoria(),

                // Columna 5.
                producto.getValorStock()
        });


        // Actualizamos el total.
        actualizarTotal();

        // Limpiamos el formulario.
        limpiarFormulario();


        // Informamos que la operación fue exitosa.
        JOptionPane.showMessageDialog(
                this,
                "Producto agregado correctamente.",
                "Información",
                JOptionPane.INFORMATION_MESSAGE
        );
    }


    // ========================================================
    // LIMPIAR FORMULARIO
    // ========================================================

    private void limpiarFormulario() {

        // Borramos el nombre.
        txtNombre.setText("");

        // Borramos el precio.
        txtPrecio.setText("");

        // Borramos el stock.
        txtStock.setText("");

        // Seleccionamos la primera categoría.
        cmbCategoria.setSelectedIndex(0);

        // Volvemos a colocar el cursor en Nombre.
        txtNombre.requestFocus();
    }


    // ========================================================
    // ELIMINAR PRODUCTO
    // ========================================================

    private void eliminarProducto() {

        // getSelectedRow() devuelve el índice
        // de la fila seleccionada.
        int filaSeleccionada =
                tabla.getSelectedRow();


        // Si devuelve -1 no hay selección.
        if (filaSeleccionada == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe seleccionar un producto.",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        // Pedimos confirmación al usuario.
        int respuesta =
                JOptionPane.showConfirmDialog(
                        this,
                        "¿Está seguro de eliminar el producto?",
                        "Confirmar eliminación",
                        JOptionPane.YES_NO_OPTION
                );


        // Comprobamos si respondió "Sí".
        if (respuesta == JOptionPane.YES_OPTION) {

            // Eliminamos la fila.
            modelo.removeRow(filaSeleccionada);

            // Recalculamos el total.
            actualizarTotal();
        }
    }
    private void modificarProducto() {
int fila = tabla.getSelectedRow();
if (filaModificar != -1) {
    modelo.setValueAt(txtNombre.getText(), filaModificar, 0);
    modelo.setValueAt(txtPrecio.getText(), filaModificar, 1);
    modelo.setValueAt(txtStock.getText(), filaModificar, 2);
    modelo.setValueAt(cmbCategoria.getSelectedItem(), filaModificar, 3);

    limpiarFormulario();
    filaModificar = -1;
    actualizarTotal();
    return;
}
 if (fila == -1) {
        JOptionPane.showMessageDialog(this, "Seleccioná un producto.");
        return;
    }
    int filaModelo = tabla.convertRowIndexToModel(fila);
    filaModificar = filaModelo;
    String nombre = modelo.getValueAt(filaModelo, 0).toString();
    double precio = Double.parseDouble(modelo.getValueAt(filaModelo, 1).toString());
    int stock = Integer.parseInt(modelo.getValueAt(filaModelo, 2).toString());
    String categoria = modelo.getValueAt(filaModelo, 3).toString();
    txtNombre.setText(nombre);
    txtPrecio.setText(String.valueOf(precio));
    txtStock.setText(String.valueOf(stock));
    cmbCategoria.setSelectedItem(categoria);
    JOptionPane.showMessageDialog(this, "Ahora podés modificar los datos.");

    
   
}

    // ========================================================
    // ACTUALIZAR TOTAL
    // ========================================================

    private void actualizarTotal() {

        // Comenzamos en cero.
        double total = 0;


        // Recorremos todas las filas.
        for (
                int i = 0;
                i < modelo.getRowCount();
                i++
        ) {

            // Obtenemos la columna 4,
            // que corresponde a Valor Stock.
            double valor =
                    Double.parseDouble(
                            modelo
                                    .getValueAt(i, 4)
                                    .toString()
                    );

            // Sumamos el valor.
            total += valor;
        }


        // Actualizamos el JLabel.
        lblTotal.setText(
                String.format(
                        "Valor total del stock: $%.2f",
                        total
                )
        );
    }


    // ========================================================
    // MÉTODO MAIN
    // ========================================================

    // Punto de entrada de nuestro programa.
    public static void main(String[] args) {

        // invokeLater ejecuta la creación de la GUI
        // en el hilo de eventos de Swing.
        SwingUtilities.invokeLater(() -> {

            // Creamos la ventana.
            GestorProductos ventana =
                    new GestorProductos();

            // Mostramos la ventana.
            ventana.setVisible(true);
        });
    }
}

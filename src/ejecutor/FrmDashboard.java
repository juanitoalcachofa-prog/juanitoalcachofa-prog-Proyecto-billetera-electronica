package ejecutor;

import acciones.Usuario;
import sistema.SistemaBilletera;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class FrmDashboard extends javax.swing.JFrame {
    
    private Usuario usuarioActual;
    private SistemaBilletera sistema;

    
    public FrmDashboard(Usuario u, SistemaBilletera s) {
        this.usuarioActual = u;
        this.sistema = s;
        initComponents();

        lblHola.setText("Bienvenido, " + usuarioActual.getNombreUsuario());
        lblSaldoTotal.setText("Saldo Total: $" + String.format("%.2f", usuarioActual.getSaldoTotal()));
        
        cargarTablaCuentas();
        cargarAsesoria(); 
        cargarTablaPrestamos(); 
        cargarNotificaciones();
        
                // --- MAGIA DEL GRÁFICO (AGREGAR AL FINAL DEL CONSTRUCTOR) ---
                PanelGraficoPastel miGrafico = new PanelGraficoPastel(usuarioActual);

                // Forzamos un tamaño para que el Layout no lo colapse
                miGrafico.setPreferredSize(new java.awt.Dimension(350, 200));

                panelContenedorGrafico.setLayout(new java.awt.BorderLayout());
                panelContenedorGrafico.add(miGrafico, java.awt.BorderLayout.CENTER);

                // REVALIDATE es clave cuando agregas cosas después de initComponents
                panelContenedorGrafico.revalidate();
                panelContenedorGrafico.repaint();
        }
    private void cargarAsesoria() {
        
    java.util.List<String> consejos = acciones.AnalisisFinanzas.generarConsejosDetallados(usuarioActual);
    StringBuilder textoFinal = new StringBuilder();
        for (String linea : consejos) {
            textoFinal.append(linea).append("\n");
        }
        
    txtConsejos.setText(textoFinal.toString());
    }
    
    public FrmDashboard() {
        initComponents();
    }

    private void cargarTablaCuentas() {
        DefaultTableModel modelo = (DefaultTableModel) tablaCuentas.getModel();
        modelo.setRowCount(0);
        for (acciones.Cuenta c : usuarioActual.getListaCuentas()) {
            Object[] fila = { c.getIdCuenta(), "Débito", "$" + String.format("%.2f", c.getSaldoActual()), "Activa" };
            modelo.addRow(fila);
        }
    }
    
    private void cargarTablaPrestamos() {
    DefaultTableModel modelo = (DefaultTableModel) tablaPrestamos.getModel();
    modelo.setRowCount(0);
    
        for (acciones.Prestamo p : usuarioActual.getListaPrestamos()) {
            Object[] fila = {
                p.getIdPrestamo(),
                "$" + String.format("%.2f", p.getMontoSolicitado()),
                String.format("%.1f", p.getMesesPagados()) + "/" + p.getCantidadMeses(), // Redondeado proporcional
                "$" + String.format("%.2f", p.getCuotaMensual()),
                p.getEstadoPrestamo()
            };
            modelo.addRow(fila);
        }
    }
    
    private void cargarNotificaciones() {
    StringBuilder sb = new StringBuilder("--- BANDEJA DE ENTRADA ---\n\n");
    for (acciones.Notificacion n : sistema.getListaNotificaciones()) {
        if (n.getIdUsuarioPropietario() == usuarioActual.getIdUsuario()) {
            sb.append("[").append(n.getFechaNotificacion()).append("]\n");
            sb.append(n.getMensajeNotificacion()).append("\n------------------------\n");
        }
    }
    txtNotificaciones.setText(sb.toString());
}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSeparator1 = new javax.swing.JSeparator();
        jPanel2 = new javax.swing.JPanel();
        lblHola = new javax.swing.JLabel();
        lblSaldoTotal = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaCuentas = new javax.swing.JTable();
        btnSalir = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtConsejos = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaPrestamos = new javax.swing.JTable();
        btnPagarCuota = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtNotificaciones = new javax.swing.JTextArea();
        btnTransferir = new javax.swing.JButton();
        btnRegistrarGasto = new javax.swing.JButton();
        btnDepositar = new javax.swing.JButton();
        btnCrearCuenta = new javax.swing.JButton();
        btnRegistrarPrestamo = new javax.swing.JButton();
        btnPerfil = new javax.swing.JButton();
        panelContenedorGrafico = new javax.swing.JPanel();

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblHola.setText("Hola muy buenos dias bienvenido :)");

        lblSaldoTotal.setText("Saldo");

        jScrollPane1.setWheelScrollingEnabled(false);

        tablaCuentas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID Cuenta", "Tipo", "Saldo", "Estado"
            }
        ));
        jScrollPane1.setViewportView(tablaCuentas);

        btnSalir.setText("Salir");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        txtConsejos.setEditable(false);
        txtConsejos.setColumns(20);
        txtConsejos.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        txtConsejos.setRows(5);
        jScrollPane2.setViewportView(txtConsejos);

        tablaPrestamos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "Monto", "Record", "Cuota mensual"
            }
        ));
        jScrollPane3.setViewportView(tablaPrestamos);
        if (tablaPrestamos.getColumnModel().getColumnCount() > 0) {
            tablaPrestamos.getColumnModel().getColumn(0).setPreferredWidth(50);
            tablaPrestamos.getColumnModel().getColumn(1).setPreferredWidth(50);
            tablaPrestamos.getColumnModel().getColumn(2).setPreferredWidth(100);
            tablaPrestamos.getColumnModel().getColumn(3).setPreferredWidth(150);
        }

        btnPagarCuota.setText("Pagar Cuota");
        btnPagarCuota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPagarCuotaActionPerformed(evt);
            }
        });

        txtNotificaciones.setEditable(false);
        txtNotificaciones.setColumns(20);
        txtNotificaciones.setLineWrap(true);
        txtNotificaciones.setRows(5);
        jScrollPane4.setViewportView(txtNotificaciones);

        btnTransferir.setText("Transferir dinero");
        btnTransferir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTransferirActionPerformed(evt);
            }
        });

        btnRegistrarGasto.setText("Registrar Gasto");
        btnRegistrarGasto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarGastoActionPerformed(evt);
            }
        });

        btnDepositar.setText("Depositar");
        btnDepositar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDepositarActionPerformed(evt);
            }
        });

        btnCrearCuenta.setText("Crear cuenta");
        btnCrearCuenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearCuentaActionPerformed(evt);
            }
        });

        btnRegistrarPrestamo.setText("Registrar Préstamo");
        btnRegistrarPrestamo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarPrestamoActionPerformed(evt);
            }
        });

        btnPerfil.setText("Actualizar Ingresos");

        panelContenedorGrafico.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout panelContenedorGraficoLayout = new javax.swing.GroupLayout(panelContenedorGrafico);
        panelContenedorGrafico.setLayout(panelContenedorGraficoLayout);
        panelContenedorGraficoLayout.setHorizontalGroup(
            panelContenedorGraficoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelContenedorGraficoLayout.setVerticalGroup(
            panelContenedorGraficoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(lblHola, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblSaldoTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addGap(0, 0, Short.MAX_VALUE)
                            .addComponent(btnPagarCuota, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(49, 49, 49)
                            .addComponent(btnRegistrarPrestamo)
                            .addGap(51, 51, 51)
                            .addComponent(btnTransferir)
                            .addGap(41, 41, 41)
                            .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnDepositar, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(90, 90, 90)
                                .addComponent(btnRegistrarGasto)
                                .addGap(103, 103, 103)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnPerfil)
                                .addGap(75, 75, 75)
                                .addComponent(btnCrearCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(panelContenedorGrafico, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jScrollPane1)
                    .addComponent(jScrollPane3)
                    .addComponent(jScrollPane4))
                .addContainerGap(17, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(lblHola, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblSaldoTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelContenedorGrafico, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDepositar)
                    .addComponent(btnRegistrarGasto)
                    .addComponent(btnCrearCuenta)
                    .addComponent(btnPerfil))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPagarCuota)
                    .addComponent(btnTransferir)
                    .addComponent(btnSalir)
                    .addComponent(btnRegistrarPrestamo))
                .addGap(36, 36, 36))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
                  
            new FrmLogin().setVisible(true); // Abre el login de nuevo
            this.dispose(); // Cierra el dashboard 
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnPagarCuotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPagarCuotaActionPerformed
    
    int filaP = tablaPrestamos.getSelectedRow();
    int filaC = tablaCuentas.getSelectedRow();
    
    if (filaP == -1 || filaC == -1) {
        JOptionPane.showMessageDialog(this, "Selecciona una cuenta y un préstamo.");
        return;
    }

    int idP = (int) tablaPrestamos.getValueAt(filaP, 0);
    int idC = (int) tablaCuentas.getValueAt(filaC, 0);
    
    acciones.Prestamo p = null;
    for(acciones.Prestamo item : usuarioActual.getListaPrestamos()) if(item.getIdPrestamo() == idP) p = item;
    
    acciones.Cuenta c = null;
    for(acciones.Cuenta item : usuarioActual.getListaCuentas()) if(item.getIdCuenta() == idC) c = item;

    String mStr = JOptionPane.showInputDialog(this, "¿Cuánto vas a pagar?", p.getCuotaMensual());
    if (mStr == null || mStr.isEmpty()) return;
    double monto = Double.parseDouble(mStr);

    if (c.retirar(monto, sistema.getListaNotificaciones())) {
        p.registrarPago(monto);

        sistema.actualizarPrestamosArchivo();
        sistema.guardarCuentas();
        sistema.guardarNotificaciones();
        
        JOptionPane.showMessageDialog(this, "¡Pago realizado!");

        lblSaldoTotal.setText("Saldo: $" + String.format("%.2f", usuarioActual.getSaldoTotal()));
        cargarTablaCuentas();
        cargarTablaPrestamos();
        cargarAsesoria();
    } else {
        JOptionPane.showMessageDialog(this, "Saldo insuficiente.");
    }
    }//GEN-LAST:event_btnPagarCuotaActionPerformed

    private void btnTransferirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTransferirActionPerformed
    
    int fila = tablaCuentas.getSelectedRow();
    if (fila == -1) {
        JOptionPane.showMessageDialog(this, "Selecciona una cuenta de origen en la tabla.");
        return;
    }
    int idOrigen = (int) tablaCuentas.getValueAt(fila, 0);
    acciones.Cuenta cOrigen = null;
    for(acciones.Cuenta c : usuarioActual.getListaCuentas()) if(c.getIdCuenta() == idOrigen) cOrigen = c;

    String idDestStr = JOptionPane.showInputDialog(this, "ID de la cuenta destino:");
    if (idDestStr == null) return;
    int idDestino = Integer.parseInt(idDestStr);

    String montoStr = JOptionPane.showInputDialog(this, "¿Cuánto deseas transferir?");
    if (montoStr == null) return;
    double monto = Double.parseDouble(montoStr);

    if (sistema.transferirDinero(usuarioActual, cOrigen, idDestino, monto)) {
        JOptionPane.showMessageDialog(this, "¡Transferencia de $" + String.format("%.2f", monto) + " exitosa!");

        lblSaldoTotal.setText("Saldo: $" + String.format("%.2f", usuarioActual.getSaldoTotal()));
        cargarTablaCuentas();
        cargarNotificaciones();
        cargarAsesoria();
    } else {
        JOptionPane.showMessageDialog(this, "No se pudo realizar. Revisa el ID destino y tu saldo.");
    }
    }//GEN-LAST:event_btnTransferirActionPerformed

    private void btnRegistrarGastoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarGastoActionPerformed
        int fila = tablaCuentas.getSelectedRow();
    if (fila == -1) {
        JOptionPane.showMessageDialog(this, "Selecciona una cuenta en la tabla de arriba.");
        return;
    }
    int idCuenta = (int) tablaCuentas.getValueAt(fila, 0);
    acciones.Cuenta cuentaElegida = null;
    for(acciones.Cuenta c : usuarioActual.getListaCuentas()) if(c.getIdCuenta() == idCuenta) cuentaElegida = c;

    String[] categorias = {"Alimentación", "Transporte", "Salud", "Hogar", "Entretenimiento", "Otros"};
    String cat = (String) JOptionPane.showInputDialog(this, "Selecciona la categoría del gasto:", 
            "Categoría", JOptionPane.QUESTION_MESSAGE, null, categorias, categorias[0]);
    if (cat == null) return;

    String montoStr = JOptionPane.showInputDialog(this, "¿Cuánto gastaste?");
    if (montoStr == null) return;
    double monto = Double.parseDouble(montoStr);

    if (cuentaElegida.retirar(monto, sistema.getListaNotificaciones())) {

        int idG = sistema.getListaGastos().stream().mapToInt(acciones.Gasto::getIdGasto).max().orElse(0) + 1;
        acciones.Gasto nuevoGasto = new acciones.Gasto(idG, usuarioActual.getIdUsuario(), monto, cat, java.time.LocalDate.now());
        
        sistema.getListaGastos().add(nuevoGasto);
        usuarioActual.agregarGasto(nuevoGasto);

        sistema.guardarGastos();
        sistema.guardarCuentas();
        sistema.guardarNotificaciones();
        
        JOptionPane.showMessageDialog(this, "¡Gasto registrado con éxito!");

        lblSaldoTotal.setText("Saldo: $" + String.format("%.2f", usuarioActual.getSaldoTotal()));
        cargarTablaCuentas();
        cargarNotificaciones();
        cargarAsesoria();
    } else {
        JOptionPane.showMessageDialog(this, "Saldo insuficiente en la cuenta elegida.");
    }
    }//GEN-LAST:event_btnRegistrarGastoActionPerformed

    private void btnDepositarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDepositarActionPerformed
        int fila = tablaCuentas.getSelectedRow();
    if (fila == -1) {
        JOptionPane.showMessageDialog(this, "Selecciona una cuenta en la tabla.");
        return;
    }
    int idCuenta = (int) tablaCuentas.getValueAt(fila, 0);
    acciones.Cuenta cElegida = null;
    for(acciones.Cuenta c : usuarioActual.getListaCuentas()) if(c.getIdCuenta() == idCuenta) cElegida = c;

    String montoStr = JOptionPane.showInputDialog(this, "¿Cuánto deseas depositar?");
    if (montoStr == null) return;
    double monto = Double.parseDouble(montoStr);

    cElegida.depositar(monto, sistema.getListaNotificaciones());
    sistema.guardarCuentas();
    sistema.guardarNotificaciones();
    
    JOptionPane.showMessageDialog(this, "¡Depósito exitoso!");
    cargarTablaCuentas();
    lblSaldoTotal.setText("Saldo: $" + String.format("%.2f", usuarioActual.getSaldoTotal()));
    }//GEN-LAST:event_btnDepositarActionPerformed

    private void btnCrearCuentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearCuentaActionPerformed
        String saldoStr = JOptionPane.showInputDialog(this, "¿Con cuánto saldo inicial deseas abrir la cuenta?");
    if (saldoStr == null) return;
    double saldo = Double.parseDouble(saldoStr);

    sistema.crearCuenta(usuarioActual, saldo);
    JOptionPane.showMessageDialog(this, "¡Nueva cuenta bancaria abierta!");
    cargarTablaCuentas();
    lblSaldoTotal.setText("Saldo: $" + String.format("%.2f", usuarioActual.getSaldoTotal()));
    }//GEN-LAST:event_btnCrearCuentaActionPerformed

    private void btnRegistrarPrestamoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarPrestamoActionPerformed
        String montoStr = JOptionPane.showInputDialog(this, "¿Cuánto dinero necesitas?");
        if (montoStr == null) return;
        double monto = Double.parseDouble(montoStr);

        String mesesStr = JOptionPane.showInputDialog(this, "¿En cuántos meses lo pagarás?");
        if (mesesStr == null) return;
        int meses = Integer.parseInt(mesesStr);

        String tipo = JOptionPane.showInputDialog(this, "Tipo de préstamo (Personal, Auto, etc.):");
        if (tipo == null) return;

        String tasaStr = JOptionPane.showInputDialog(this, "¿Qué tasa de interés anual? (Ej: 0.10 para 10%)");
        if (tasaStr == null) return;
        double tasa = Double.parseDouble(tasaStr); 

        sistema.registrarPrestamo(usuarioActual, monto, meses, tipo, tasa);

        JOptionPane.showMessageDialog(this, "¡Préstamo aprobado e ingresado a tu cuenta!");

        cargarTablaPrestamos();
        cargarTablaCuentas();
        lblSaldoTotal.setText("Saldo: $" + String.format("%.2f", usuarioActual.getSaldoTotal()));
    }//GEN-LAST:event_btnRegistrarPrestamoActionPerformed
    private PanelGraficoPastel panelGrafico;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCrearCuenta;
    private javax.swing.JButton btnDepositar;
    private javax.swing.JButton btnPagarCuota;
    private javax.swing.JButton btnPerfil;
    private javax.swing.JButton btnRegistrarGasto;
    private javax.swing.JButton btnRegistrarPrestamo;
    private javax.swing.JButton btnSalir;
    private javax.swing.JButton btnTransferir;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblHola;
    private javax.swing.JLabel lblSaldoTotal;
    private javax.swing.JPanel panelContenedorGrafico;
    private javax.swing.JTable tablaCuentas;
    private javax.swing.JTable tablaPrestamos;
    private javax.swing.JTextArea txtConsejos;
    private javax.swing.JTextArea txtNotificaciones;
    // End of variables declaration//GEN-END:variables
class PanelGraficoPastel extends javax.swing.JPanel {
    private acciones.Usuario usuario;

    public PanelGraficoPastel(acciones.Usuario u) {
        this.usuario = u;
    }

    @Override
    protected void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);
        if (usuario == null) return;

        java.awt.Graphics2D g2d = (java.awt.Graphics2D) g;
        
        g2d.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                        java.awt.RenderingHints.VALUE_ANTIALIAS_ON);

        double ingresos = usuario.getIngresoMensual();
        double gastos = usuario.getGastosTotales();
        double deudas = usuario.getDeudasTotales();  
        double total = ingresos + gastos + deudas;  
        
            g2d.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
            g2d.setColor(java.awt.Color.BLACK);
            g2d.drawString("Balance General", 10, 20);
            
        if (total == 0) {
            g2d.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
            g2d.drawString("Aún no hay movimientos.", 20, 100);
            g2d.drawString("¡Empieza a registrar tus finanzas!", 20, 120);
            return;
        }
                        double pIng = (ingresos / total) * 100;
                        double pGas = (gastos / total) * 100;
                        double pDeu = (deudas / total) * 100;

        int anguloIngresos = (int) Math.round((ingresos / total) * 360);
        int anguloGastos = (int) Math.round((gastos / total) * 360);
        int anguloDeudas = 360 - (anguloIngresos + anguloGastos);

        int x = 20, y = 40, width = 130, height = 130;

        
            g2d.setColor(new java.awt.Color(46, 204, 113)); // Verde
            g2d.fillArc(x, y, width, height, 0, anguloIngresos);

            g2d.setColor(new java.awt.Color(231, 76, 60)); // Rojo
            g2d.fillArc(x, y, width, height, anguloIngresos, anguloGastos);

            g2d.setColor(new java.awt.Color(230, 126, 34)); // Naranja
            g2d.fillArc(x, y, width, height, anguloIngresos + anguloGastos, anguloDeudas);
        
            g2d.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 11));
            int leyX = 165, leyY = 60, sBox = 12;


            g2d.setColor(new java.awt.Color(46, 204, 113));
            g2d.fillRect(leyX, leyY, sBox, sBox);
            g2d.setColor(java.awt.Color.BLACK);
            g2d.drawString(String.format("Ingresos: $%.2f (%.1f%%)", ingresos, pIng), leyX + 18, leyY + 10);

            leyY += 25;
            g2d.setColor(new java.awt.Color(231, 76, 60));
            g2d.fillRect(leyX, leyY, sBox, sBox);
            g2d.setColor(java.awt.Color.BLACK);
            g2d.drawString(String.format("Gastos: $%.2f (%.1f%%)", gastos, pGas), leyX + 18, leyY + 10);

            leyY += 25;
            g2d.setColor(new java.awt.Color(230, 126, 34));
            g2d.fillRect(leyX, leyY, sBox, sBox);
            g2d.setColor(java.awt.Color.BLACK);
            g2d.drawString(String.format("Deudas: $%.2f (%.1f%%)", deudas, pDeu), leyX + 18, leyY + 10);
    }
}
}

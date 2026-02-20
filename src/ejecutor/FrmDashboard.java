package ejecutor;

import acciones.Usuario;
import sistema.SistemaBilletera;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;

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
    }
    private void cargarAsesoria() {
    // 1. Obtenemos la lista de consejos desde la lógica
    java.util.List<String> consejos = acciones.AnalisisFinanzas.generarConsejosDetallados(usuarioActual);
    
    // 2. Los unimos todos en un solo texto
    StringBuilder textoFinal = new StringBuilder();
    for (String linea : consejos) {
        textoFinal.append(linea).append("\n");
    }
    
    // 3. Lo mostramos en el cuadro de texto
    txtConsejos.setText(textoFinal.toString());
}
    public FrmDashboard() {
        initComponents();
    }
    // EL MÉTODO DEBE IR AQUÍ ADENTRO:
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
    // Filtramos las notificaciones que son para este usuario
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblHola.setText("Hola muy buenos dias bienvenido :)");

        lblSaldoTotal.setText("jLabel2");

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
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(tablaPrestamos);

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblHola, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane4)
                            .addComponent(lblSaldoTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
                            .addComponent(jScrollPane2)
                            .addComponent(jScrollPane3))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(34, 34, 34)
                                .addComponent(btnSalir)
                                .addGap(19, 19, 19))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnTransferir)
                                    .addComponent(btnPagarCuota, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblHola, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblSaldoTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 31, Short.MAX_VALUE)
                        .addComponent(btnPagarCuota)
                        .addGap(83, 83, 83)
                        .addComponent(btnTransferir)
                        .addGap(100, 100, 100)
                        .addComponent(btnSalir)
                        .addGap(24, 24, 24))))
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
    // 2. Usar TUS objetos actuales
    int idP = (int) tablaPrestamos.getValueAt(filaP, 0);
    int idC = (int) tablaCuentas.getValueAt(filaC, 0);
    
    acciones.Prestamo p = null;
    for(acciones.Prestamo item : usuarioActual.getListaPrestamos()) if(item.getIdPrestamo() == idP) p = item;
    
    acciones.Cuenta c = null;
    for(acciones.Cuenta item : usuarioActual.getListaCuentas()) if(item.getIdCuenta() == idC) c = item;
    // 3. Preguntar monto (JOptionPane es estándar de Java)
    String mStr = JOptionPane.showInputDialog(this, "¿Cuánto vas a pagar?", p.getCuotaMensual());
    if (mStr == null || mStr.isEmpty()) return;
    double monto = Double.parseDouble(mStr);
    // 4. USAR TUS MÉTODOS ORIGINALES (retirar y registrarPago)
    if (c.retirar(monto, sistema.getListaNotificaciones())) {
        p.registrarPago(monto); // <--- Este es TU método
        
        // 5. Guardar usando TUS métodos de SistemaBilletera
        sistema.actualizarPrestamosArchivo();
        sistema.guardarCuentas();
        sistema.guardarNotificaciones();
        
        JOptionPane.showMessageDialog(this, "¡Pago realizado!");
        
        // Refrescar la vista
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

    // 2. Pedir datos de destino
    String idDestStr = JOptionPane.showInputDialog(this, "ID de la cuenta destino:");
    if (idDestStr == null) return;
    int idDestino = Integer.parseInt(idDestStr);

    String montoStr = JOptionPane.showInputDialog(this, "¿Cuánto deseas transferir?");
    if (montoStr == null) return;
    double monto = Double.parseDouble(montoStr);

    // 3. Ejecutar transferencia
    if (sistema.transferirDinero(usuarioActual, cOrigen, idDestino, monto)) {
        JOptionPane.showMessageDialog(this, "¡Transferencia de $" + String.format("%.2f", monto) + " exitosa!");
        // Refrescar vista
        lblSaldoTotal.setText("Saldo: $" + String.format("%.2f", usuarioActual.getSaldoTotal()));
        cargarTablaCuentas();
        cargarNotificaciones();
        cargarAsesoria();
    } else {
        JOptionPane.showMessageDialog(this, "No se pudo realizar. Revisa el ID destino y tu saldo.");
    }
    }//GEN-LAST:event_btnTransferirActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPagarCuota;
    private javax.swing.JButton btnSalir;
    private javax.swing.JButton btnTransferir;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lblHola;
    private javax.swing.JLabel lblSaldoTotal;
    private javax.swing.JTable tablaCuentas;
    private javax.swing.JTable tablaPrestamos;
    private javax.swing.JTextArea txtConsejos;
    private javax.swing.JTextArea txtNotificaciones;
    // End of variables declaration//GEN-END:variables
}

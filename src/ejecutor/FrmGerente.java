package ejecutor;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class FrmGerente extends javax.swing.JFrame {
    
    private acciones.Gerente gerenteActual;
    private sistema.SistemaBilletera sistema;

    
    public FrmGerente(acciones.Gerente g, sistema.SistemaBilletera s) {
    this.gerenteActual = g;
    this.sistema = s;
    initComponents();
    
    setTitle("Panel Administrativo - " + gerenteActual.getNombreGerente());
    cargarTablaUsuarios();
}

    public FrmGerente() {
        initComponents();
    }
    
private void cargarTablaUsuarios() {
    DefaultTableModel modelo = (DefaultTableModel) tablaUsuarios.getModel();
    modelo.setRowCount(0);
    
    for (acciones.Usuario u : sistema.listaUsuarios) {

        double ingresos = u.getIngresoMensual();
        double gastos = u.getGastosTotales();
        double ratio = (ingresos > 0) ? (gastos / ingresos) : 1.0;

        String estado = (ratio > 0.8) ? "CRÍTICO" : 
                       (ratio > 0.5) ? "REGULAR" : "ESTABLE";

        Object[] fila = {
            u.getIdUsuario(),
            u.getNombreUsuario(),
            "$" + String.format("%.2f", u.getSaldoTotal()),
            estado
        };
        modelo.addRow(fila);
            tablaUsuarios.getColumnModel().getColumn(3).setCellRenderer(new javax.swing.table.DefaultTableCellRenderer() {
        @Override
        public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value, 
                boolean isSelected, boolean hasFocus, int row, int column) {
            java.awt.Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            
            String texto = (value != null) ? value.toString() : "";
            if (texto.contains("CRÍTICO")) c.setForeground(java.awt.Color.RED);
            else if (texto.contains("REGULAR")) c.setForeground(java.awt.Color.ORANGE);
            else if (texto.contains("ESTABLE")) c.setForeground(new java.awt.Color(0, 153, 51));
            else c.setForeground(table.getForeground());
            
            return c;
        }
    });
    }
}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tablaUsuarios = new javax.swing.JTable();
        txtMensajeAdmin = new javax.swing.JTextField();
        btnEnviarAviso = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtNotificacionesUsuario = new javax.swing.JTextArea();
        btnVerNotificaciones = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tablaUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "Nombre", "Fondos", "Prestamos"
            }
        ));
        jScrollPane1.setViewportView(tablaUsuarios);
        if (tablaUsuarios.getColumnModel().getColumnCount() > 0) {
            tablaUsuarios.getColumnModel().getColumn(0).setPreferredWidth(40);
            tablaUsuarios.getColumnModel().getColumn(1).setPreferredWidth(100);
            tablaUsuarios.getColumnModel().getColumn(2).setPreferredWidth(100);
            tablaUsuarios.getColumnModel().getColumn(3).setPreferredWidth(250);
        }

        txtMensajeAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMensajeAdminActionPerformed(evt);
            }
        });

        btnEnviarAviso.setText("Enviar");
        btnEnviarAviso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnviarAvisoActionPerformed(evt);
            }
        });

        txtNotificacionesUsuario.setColumns(20);
        txtNotificacionesUsuario.setRows(5);
        jScrollPane2.setViewportView(txtNotificacionesUsuario);

        btnVerNotificaciones.setText("Ver notificaciones");
        btnVerNotificaciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerNotificacionesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 469, Short.MAX_VALUE)
                    .addComponent(txtMensajeAdmin))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnEnviarAviso, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnVerNotificaciones))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnEnviarAviso)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnVerNotificaciones))
                    .addComponent(txtMensajeAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(80, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEnviarAvisoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnviarAvisoActionPerformed
        int fila = tablaUsuarios.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un usuario de la lista.");
            return;
        }

        int idU = (int) tablaUsuarios.getValueAt(fila, 0);
        acciones.Usuario uDestino = sistema.buscarUsuarioPorId(idU);
        String msg = txtMensajeAdmin.getText();

        if (msg.isEmpty()) return;

        gerenteActual.enviarMensajePersonalizado(uDestino, msg, sistema.listaNotificaciones);
        sistema.guardarNotificaciones();

        JOptionPane.showMessageDialog(this, "Mensaje enviado a " + uDestino.getNombreUsuario());
        txtMensajeAdmin.setText("");
    }//GEN-LAST:event_btnEnviarAvisoActionPerformed

    private void txtMensajeAdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMensajeAdminActionPerformed

    }//GEN-LAST:event_txtMensajeAdminActionPerformed

    private void btnVerNotificacionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerNotificacionesActionPerformed
        int fila = tablaUsuarios.getSelectedRow();
    if (fila == -1) return;
    
    int idU = (int) tablaUsuarios.getValueAt(fila, 0);
    StringBuilder sb = new StringBuilder("--- NOTIFICACIONES DEL USUARIO ---\n\n");
    
    for (acciones.Notificacion n : sistema.listaNotificaciones) {
        if (n.getIdUsuarioPropietario() == idU) {
            sb.append("[").append(n.getFechaNotificacion()).append("]\n");
            sb.append(n.getMensajeNotificacion()).append("\n----------------\n");
        }
    }
    txtNotificacionesUsuario.setText(sb.toString());
    }//GEN-LAST:event_btnVerNotificacionesActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEnviarAviso;
    private javax.swing.JButton btnVerNotificaciones;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tablaUsuarios;
    private javax.swing.JTextField txtMensajeAdmin;
    private javax.swing.JTextArea txtNotificacionesUsuario;
    // End of variables declaration//GEN-END:variables
}

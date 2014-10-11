//    Wanda POS - Africa's Gift to the World
//    Copyright (c) 2014-2015 IT-Kamer & previous Unicenta POS and Openbravo POS works
//    www.erp-university-africa.com
//
//    This file is part of Wanda POS
//
//    Wanda POS is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//   Wanda POS is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with Wanda POS.  If not, see <http://www.gnu.org/licenses/>.

package com.openbravo.pos.forms;

import com.openbravo.pos.config.JFrmConfig;
import com.openbravo.pos.instance.AppMessage;
import com.openbravo.pos.instance.InstanceManager;
import com.openbravo.pos.util.OSValidator;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import javax.swing.JFrame;

/**
 *
 * @author  adrianromero
 */
public class JRootKiosk extends javax.swing.JFrame implements AppMessage {
    
    private InstanceManager m_instmanager = null;
    
    private JRootApp m_rootapp;
    private AppProperties m_props;
    private OSValidator m_OS;    
    
    /** Creates new form JRootKiosk */
    public JRootKiosk() {
        
        setUndecorated(true);
        setResizable(false);
        
        initComponents();
    }

    /**
     *
     * @param props
     */
    public void initFrame(AppProperties props) {
        
        m_OS = new OSValidator();
        m_props = props;
        
        m_rootapp = new JRootApp();
        
        if (m_rootapp.initApp(m_props)) {
            
            if ("true".equals(props.getProperty("machine.uniqueinstance"))) {
                // Register the running application
                try {
                    m_instmanager = new InstanceManager(this);
// JG 16 May 12 use multicatch
                } catch (RemoteException | AlreadyBoundException e) {
                }
            }
        
            // Show the application
            add(m_rootapp, BorderLayout.CENTER);            
    
// With John L's sub[dot] updates
//            setTitle(AppLocal.APP_NAME + " - " + AppLocal.APP_VERSION+ " - " + AppLocal.APP_VERSIONJL + "-" + AppLocal.APP_VERSIONJLCORE);

            setTitle(AppLocal.APP_NAME + " - " + AppLocal.APP_VERSION);
            
            Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
            setBounds(0, 0, d.width, d.height);        
            
            setVisible(true);                        
        } else {
            new JFrmConfig(props).setVisible(true); // Show the configuration window.
        }        
    }
    
    /**
     *
     * @throws RemoteException
     */
    @Override
    public void restoreWindow() throws RemoteException {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                if (getExtendedState() == JFrame.ICONIFIED) {
                    setExtendedState(JFrame.NORMAL);
                }
                requestFocus();
            }
        });
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing

        m_rootapp.tryToClose();

    }//GEN-LAST:event_formWindowClosing

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed

        System.exit(0);
        
    }//GEN-LAST:event_formWindowClosed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    
}

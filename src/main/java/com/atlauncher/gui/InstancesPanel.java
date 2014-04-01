/**
 * Copyright 2013-2014 by ATLauncher and Contributors
 *
 * This work is licensed under the Creative Commons Attribution-ShareAlike 3.0 Unported License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/3.0/.
 */
package com.atlauncher.gui;

import com.atlauncher.App;
import com.atlauncher.data.Instance;
import com.atlauncher.utils.Localizer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.regex.Pattern;

public class InstancesPanel extends JPanel {

    private JPanel topPanel;
    private JButton clearButton;
    private JTextField searchBox;
    private JButton searchButton;
    private JCheckBox hasUpdate;
    private JLabel hasUpdateLabel;

    private String searchText = null;
    private boolean isUpdate = false;

    private JPanel panel;
    private JScrollPane scrollPane;
    private int currentPosition = 0;

    public InstancesPanel() {
        setLayout(new BorderLayout());
        loadContent(false);
    }

    public void loadContent(boolean keepFilters) {
        topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        clearButton = new JButton(Localizer.localize("common.clear"));
        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchBox.setText("");
                hasUpdate.setSelected(false);
                reload();
            }
        });
        topPanel.add(clearButton);

        searchBox = new JTextField(16);
        if (keepFilters) {
            searchBox.setText(this.searchText);
        }
        searchBox.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    reload();
                }
            }

            public void keyTyped(KeyEvent e) {
            }

            public void keyReleased(KeyEvent e) {
            }
        });
        topPanel.add(searchBox);

        searchButton = new JButton(Localizer.localize("common.search"));
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                reload();
            }
        });
        topPanel.add(searchButton);

        hasUpdate = new JCheckBox();
        hasUpdate.setSelected(isUpdate);
        hasUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                reload();
            }
        });
        topPanel.add(hasUpdate);

        hasUpdateLabel = new JLabel(Localizer.localize("instance.hasupdate"));
        topPanel.add(hasUpdateLabel);

        add(topPanel, BorderLayout.NORTH);

        panel = new JPanel();
        scrollPane = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;

        int count = 0;
        for (Instance instance : App.settings.getInstancesSorted()) {
            if (instance.canPlay()) {
                if (keepFilters) {
                    boolean showInstance = true;

                    if (searchText != null) {
                        if (!Pattern.compile(Pattern.quote(searchText), Pattern.CASE_INSENSITIVE)
                                .matcher(instance.getName()).find()) {
                            showInstance = false;
                        }
                    }

                    if (isUpdate) {
                        if (!instance.hasUpdate()) {
                            showInstance = false;
                        }
                    }

                    if (showInstance) {
                        panel.add(new InstanceDisplay(instance), gbc);
                        gbc.gridy++;
                        count++;
                    }
                } else {
                    panel.add(new InstanceDisplay(instance), gbc);
                    gbc.gridy++;
                    count++;
                }
            }
        }
        if (count == 0) {
            panel.add(
                    new NothingToDisplay(Localizer.localize("instance.nodisplay",
                            "\n\n")), gbc);
        }

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                scrollPane.getVerticalScrollBar().setValue(currentPosition);
            }
        });
    }

    public void reload() {
        this.currentPosition = scrollPane.getVerticalScrollBar().getValue();
        this.searchText = searchBox.getText();
        this.isUpdate = hasUpdate.isSelected();
        if (this.searchText.isEmpty()) {
            this.searchText = null;
        }
        removeAll();
        loadContent(true);
        validate();
        repaint();
        searchBox.requestFocus();
    }

}
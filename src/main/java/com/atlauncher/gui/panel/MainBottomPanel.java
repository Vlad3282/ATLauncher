package com.atlauncher.gui.panel;

import com.atlauncher.ATLauncher;
import com.atlauncher.gui.comp.AccountComboBox;
import com.atlauncher.gui.comp.StatusLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public final class MainBottomPanel extends JPanel{
	private static final long serialVersionUID = 7272200927419278735L;
	
	private final class ActionsPanel extends JPanel{
		private static final long serialVersionUID = -9134018565955990090L;
		
		private final JButton TC_BUTTON = new JButton("Hide Console"){
			private static final long serialVersionUID = 3695287462704228197L;
			
			{
				this.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent event){
						if(ATLauncher.CONSOLE.isVisible()){
							ATLauncher.LOGGER.info("Hiding Console");
							ATLauncher.CONSOLE.setVisible(false);
						} else{
							ATLauncher.LOGGER.info("Showing Console");
							ATLauncher.CONSOLE.setVisible(true);
						}
						
						setText((String.format("%s Console", (ATLauncher.CONSOLE.isVisible() ? "Hide" : "Show"))));
					}
				});
			}
		};
		private final JButton OF_BUTTON = new JButton("Open Folder"){
			private static final long serialVersionUID = 2497003804235539535L;
			
			{
				this.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent event){
						ATLauncher.openFile(ATLauncher.ROOT);
					}
				});
			}
		};
		private final JButton UPDATE_BUTTON = new JButton("Update Launcher"){
			private static final long serialVersionUID = -1382867013579028964L;
			
			{
				this.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent event){
						
					}
				});
			}
		};
		
		private final JButton[] BUTTONS = new JButton[]{
				this.TC_BUTTON, this.OF_BUTTON, this.UPDATE_BUTTON
		};
		
		protected ActionsPanel(){
			super(new GridBagLayout());
			this.setMinimumSize(new Dimension(0, 50));
			
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridx = 0;
			gbc.gridy = GridBagConstraints.RELATIVE;
			gbc.insets.set(0, 0, 0, 5);
			for(int i = 0; i < this.BUTTONS.length; i++){
				this.add(this.BUTTONS[i], gbc);
				gbc.gridx++;
			}
		}
	}
	
	private final class AccountPanel extends JPanel{
		private static final long serialVersionUID = 9098631548658827529L;
		
		private final AccountComboBox ACCOUNTS = new AccountComboBox();
		private final StatusLabel STATUS_LABEL = new StatusLabel();
		
		protected AccountPanel(){
			super(new GridBagLayout());
			
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridx = 0;
			gbc.gridy = GridBagConstraints.RELATIVE;
			gbc.insets.set(0, 0, 0, 5);
			this.add(this.ACCOUNTS, gbc);
			gbc.gridx++;
			this.add(this.STATUS_LABEL, gbc);


			StatusLabel.updateStatus(this.STATUS_LABEL, StatusLabel.STATUS_CHECKING);
		}
	}
	
	private final ActionsPanel ACTIONS_PANEL = new ActionsPanel();
	private final AccountPanel ACCOUNT_PANEL = new AccountPanel();
	private final SocialButtonsPanel SOCIAL_PANEL = new SocialButtonsPanel();

	public MainBottomPanel(){
		super(new BorderLayout());
		this.setBorder(BorderFactory.createEtchedBorder());
		this.setMinimumSize(new Dimension(0, 50));
		this.add(this.ACTIONS_PANEL, BorderLayout.WEST);
		this.add(this.ACCOUNT_PANEL, BorderLayout.CENTER);
		this.add(this.SOCIAL_PANEL, BorderLayout.EAST);
	}
}
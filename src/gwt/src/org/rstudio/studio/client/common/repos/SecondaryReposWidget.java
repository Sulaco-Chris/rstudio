/*
 * SecondaryReposWidget.java
 *
 * Copyright (C) 2009-18 by RStudio, Inc.
 *
 * Unless you have received this program directly from RStudio pursuant
 * to the terms of a commercial license agreement with RStudio, then
 * this program is licensed to you under the terms of version 3 of the
 * GNU Affero General Public License. This program is distributed WITHOUT
 * ANY EXPRESS OR IMPLIED WARRANTY, INCLUDING THOSE OF NON-INFRINGEMENT,
 * MERCHANTABILITY OR FITNESS FOR A PARTICULAR PURPOSE. Please refer to the
 * AGPL (http://www.gnu.org/licenses/agpl-3.0.txt) for more details.
 *
 */
package org.rstudio.studio.client.common.repos;

import org.rstudio.core.client.files.FileSystemItem;
import org.rstudio.core.client.widget.LabelWithHelp;
import org.rstudio.core.client.widget.MessageDialog;
import org.rstudio.core.client.widget.Operation;
import org.rstudio.core.client.widget.ProgressIndicator;
import org.rstudio.core.client.widget.ProgressOperationWithInput;
import org.rstudio.core.client.widget.SmallButton;
import org.rstudio.studio.client.RStudioGinjector;
import org.rstudio.studio.client.common.FileDialogs;
import org.rstudio.studio.client.common.GlobalDisplay;
import org.rstudio.studio.client.common.spelling.SpellingService;
import org.rstudio.studio.client.server.ServerError;
import org.rstudio.studio.client.server.ServerRequestCallback;
import org.rstudio.studio.client.workbench.model.RemoteFileSystemContext;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArrayString;
import com.google.gwt.dom.client.SelectElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.inject.Inject;

public class SecondaryReposWidget extends Composite
{
   public SecondaryReposWidget()
   {
      RStudioGinjector.INSTANCE.injectMembers(this);
      
      VerticalPanel panel = new VerticalPanel();
      
      HorizontalPanel horizontal = new HorizontalPanel();
      listBox_ = new ListBox();
      listBox_.setMultipleSelect(false);
      listBox_.addStyleName(RES.styles().listBox());
      listBox_.getElement().<SelectElement>cast().setSize(6);
      horizontal.add(listBox_);
      
      VerticalPanel buttonPanel = new VerticalPanel();
      buttonPanel.addStyleName(RES.styles().buttonPanel());

      SmallButton buttonAdd = createButton("Add...");
      buttonAdd.addClickHandler(addButtonClicked_);
      buttonPanel.add(buttonAdd);

      SmallButton buttonRemove = createButton("Remove...");
      buttonRemove.addClickHandler(removeButtonClicked_);
      buttonPanel.add(buttonRemove);

      SmallButton buttonUp = createButton("Up");
      buttonUp.addClickHandler(upButtonClicked_);
      buttonPanel.add(buttonUp);

      SmallButton buttonDown = createButton("Down");
      buttonDown.addClickHandler(downButtonClicked_);
      buttonPanel.add(buttonDown);

      horizontal.add(buttonPanel);
      
      panel.add(horizontal);
      
      initWidget(panel);
   }

   @Inject
   void initialize(GlobalDisplay globalDisplay)
   {
   }
   
   public void setRepos(JsArrayString repos)
   {
      listBox_.clear();
      for (int i = 0; i < repos.length(); i++)
         listBox_.addItem(repos.get(i));
   }
   
   private ClickHandler addButtonClicked_ = new ClickHandler() {
      @Override
      public void onClick(ClickEvent event)
      {
      }
   };
   
   private ClickHandler removeButtonClicked_ = new ClickHandler() {
      @Override
      public void onClick(ClickEvent event)
      {
         // get selected index
         int index = listBox_.getSelectedIndex();
         if (index != -1)
         {
            final String repo = listBox_.getValue(index);
            globalDisplay_.showYesNoMessage(
               MessageDialog.WARNING, 
               "Confirm Remove", 
               "Are you sure you want to remove the " + repo + " repo?",
               new Operation() {
                  @Override
                  public void execute()
                  {
                  }
               },
               false);
            
         }
      }
   };

   private ClickHandler upButtonClicked_ = new ClickHandler() {
      @Override
      public void onClick(ClickEvent event)
      {
      }
   };

   private ClickHandler downButtonClicked_ = new ClickHandler() {
      @Override
      public void onClick(ClickEvent event)
      { 
      }
   };
   
   private SmallButton createButton(String caption)
   {
      SmallButton button = new SmallButton(caption);
      button.addStyleName(RES.styles().button());
      button.fillWidth();
      return button;
   }
   
   private final ListBox listBox_;
   private GlobalDisplay globalDisplay_;
   
   static interface Styles extends CssResource
   {
      String listBox();
      String button();
      String buttonPanel();
   }
  
   static interface Resources extends ClientBundle
   {
      @Source("SecondaryReposWidget.css")
      Styles styles();
   }
   
   static Resources RES = (Resources)GWT.create(Resources.class) ;
   public static void ensureStylesInjected()
   {
      RES.styles().ensureInjected();
   }
}
package org.vaadin.jonatan.contexthelp.demo;

import java.lang.reflect.Field;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class HelpKey extends VerticalLayout implements
		Property.ValueChangeListener {

	public HelpKey() {
		super();
		try {
			addComponent(buildKeyCodeSelect());
		} catch (Exception e) {
			e.printStackTrace();
		}
		TextField tf = new TextField("Type the key here");
		ContextHelpApplication
				.getContextHelp()
				.addHelpForComponent(
						tf,
						"<h2><center>Good job!</center></h2><br/>"
								+ "<span style='color:green;'>Note</span> that the key "
								+ "changes for <em>the whole application</em>");
		addComponent(tf);

		setSpacing(true);
		setMargin(true);
	}

	private ComboBox buildKeyCodeSelect() throws UnsupportedOperationException,
			IllegalArgumentException, IllegalAccessException {
		ComboBox keyCodeBox = new ComboBox("Select a key");
		keyCodeBox.addListener(this);
		keyCodeBox.addContainerProperty("caption", String.class, "");
		keyCodeBox.setItemCaptionPropertyId("caption");
		for (Field f : KeyCode.class.getDeclaredFields()) {
			Item item = keyCodeBox.addItem(f.getInt(KeyCode.class));
			item.getItemProperty("caption").setValue(formatName(f.getName()));
		}

		ContextHelpApplication.getContextHelp().addHelpForComponent(keyCodeBox,
				"Select the key that opens this help bubble");

		keyCodeBox.select(KeyCode.F1);
		keyCodeBox.setImmediate(true);
		return keyCodeBox;
	}

	private String formatName(String name) {
		return name.replace("_", " ");
	}

	public void valueChange(ValueChangeEvent event) {
		ContextHelpApplication.getContextHelp().setHelpKey(
				(Integer) event.getProperty().getValue());
	}
}

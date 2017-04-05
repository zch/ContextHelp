package org.vaadin.jonatan.contexthelp.demo;

import java.lang.reflect.Field;
import java.util.ArrayList;

import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;


@SuppressWarnings("serial")
public class HelpKey extends VerticalLayout implements ValueChangeListener {

	public HelpKey() {
		super();
		try {
			addComponent(buildKeyCodeSelect());
		} catch (Exception e) {
			e.printStackTrace();
		}
		TextField tf = new TextField("Type the key here");
        tf.setId("configure.textfield");
		ContextHelpDemoUI
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

    public static class KeyItem {
        private Integer code;
        private String name;

        public KeyItem(Integer code, String name) {
            this.code = code;
            this.name = name;
        }

        public static String getName(Object o) {
            return ((KeyItem)o).name;
        }
    }
	private ComboBox buildKeyCodeSelect() throws UnsupportedOperationException,
			IllegalArgumentException, IllegalAccessException {
		ComboBox keyCodeBox = new ComboBox("Select a key");
		keyCodeBox.addValueChangeListener(this);
        ArrayList<KeyItem> keyCodes = new ArrayList<>();
		for (Field f : KeyCode.class.getDeclaredFields()) {
            keyCodes.add(new KeyItem(f.getInt(KeyCode.class), f.getName()));
		}
        ListDataProvider dataProvider = new ListDataProvider<>(keyCodes);
        keyCodeBox.setDataProvider(dataProvider);
        keyCodeBox.setItemCaptionGenerator(KeyItem::getName);


		ContextHelpDemoUI.getContextHelp().addHelpForComponent(keyCodeBox,
				"Select the key that opens this help bubble");

        keyCodeBox.setValue(KeyCode.F1);
		return keyCodeBox;
	}

	private String formatName(String name) {
		return name.replace("_", " ");
	}

	@Override
	public void valueChange(ValueChangeEvent event) {
        ContextHelpDemoUI.getContextHelp().setHelpKey(
                (Integer) event.getValue());
	}
}

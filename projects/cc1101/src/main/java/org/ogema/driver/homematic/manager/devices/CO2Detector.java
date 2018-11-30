/**
 * This file is part of OGEMA.
 *
 * OGEMA is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 3
 * as published by the Free Software Foundation.
 *
 * OGEMA is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with OGEMA. If not, see <http://www.gnu.org/licenses/>.
 */
package org.ogema.driver.homematic.manager.devices;

import org.ogema.driver.homematic.HomeMaticConnectionException;
import org.ogema.driver.homematic.data.FloatValue;
import org.ogema.driver.homematic.data.Value;
import org.ogema.driver.homematic.manager.Device;
import org.ogema.driver.homematic.manager.DeviceAttribute;
import org.ogema.driver.homematic.manager.MessageHandler;
import org.ogema.driver.homematic.manager.ValueType;
import org.ogema.driver.homematic.manager.messages.CommandMessage;
import org.ogema.driver.homematic.manager.messages.StatusMessage;
import org.ogema.driver.homematic.tools.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CO2Detector extends Device {

	private final Logger logger = LoggerFactory.getLogger(CO2Detector.class);

	public CO2Detector(DeviceDescriptor descriptor, MessageHandler messageHandler, String address, String deviceKey, String serial) 
			throws HomeMaticConnectionException {
		super(descriptor, messageHandler, address, deviceKey, serial);
	}

	@Override
	protected void configureChannels() {
		deviceAttributes.put((short) 0x0001, new DeviceAttribute((short) 0x0001, "Concentration", true, true, ValueType.FLOAT));
	}

	@Override
	public void parseMessage(StatusMessage msg, CommandMessage cmd, Device device) {
		byte msgType = msg.type;
		byte contentType = msg.data[0];

		if (device.getKey().equals("0056") || device.getKey().equals("009F")) {
			if ((msg.type == 0x02 && msg.data[0] == 0x01) || (msg.type == 0x10 && msg.data[0] == 0x06)
					|| (msg.type == 0x41)) {
				parseValue(msg);
			}
			else if ((msgType == 0x10 && (contentType == 0x02) || (contentType == 0x03))) {
				// Configuration response Message
				parseConfig(msg, cmd);
			}

		}
		// else if (msg.msg_type == 0x10 && msg.msg_data[0] == 0x06) {
		// // long err = Converter.toLong(msg[3]);
		// state = Converter.toLong(msg.msg_data[2]);
		// String state_str = (state > 2) ? "off" : "smoke-Alarm";
		//
		// logger.debug("Level: " + state);
		// deviceAttributes.get((short) 0x0001).setValue(new FloatValue(state));
		// // String err_str = ((err & 0x80) > 0) ? "low" : "ok";
		// logger.debug("State: " + state_str);
		// }
	}

	@Override
	public void channelChanged(byte identifier, Value value) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void parseValue(StatusMessage msg) {
		long state = 0;
		state = Converter.toLong(msg.data[2]);

		if (key.equals("009F"))
			logger.debug("Level: " + state);
		logger.debug("State: " + state);
		deviceAttributes.get((short) 0x0001).setValue(new FloatValue(state));
		logger.debug("#######################\tCO2\t#############################");
		logger.debug("Concentration: " + state);
	}

}

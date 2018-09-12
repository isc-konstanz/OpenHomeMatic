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
package org.ogema.driver.homematic.manager;

public abstract class DeviceChannel {
	protected final String description;
	protected final boolean mandatory;
	protected final ValueType valueType;

	public DeviceChannel(String description, boolean mandatory, ValueType valueType) {
		this.description = description;
		this.mandatory = mandatory;
		this.valueType = valueType;
	}

	public abstract String getIdentifier();

	public abstract String getAddress();

	public String getDescription() {
		return description;
	}

	public boolean isMandatory() {
		return mandatory;
	}

	public ValueType getValueType() {
		return valueType;
	}

}

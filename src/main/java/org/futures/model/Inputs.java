
package org.futures.model;

import org.futures.model.enums.InvestorProfile;
import org.futures.model.enums.CoffeeType;
import org.futures.model.enums.Horizon;

public record Inputs(Horizon horizon, CoffeeType coffeeType, InvestorProfile profile) {}

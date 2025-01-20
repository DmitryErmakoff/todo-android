package com.example.server.service;

import com.pi4j.io.gpio.GpioPinDigitalOutput;
import org.springframework.stereotype.Service;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

@Service
public class GPIOService {
    private final GpioPinDigitalOutput ledPin;

    public GPIOService() {
        GpioController gpio = GpioFactory.getInstance();
        ledPin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_04, "LED", PinState.LOW);
    }

    public void blinkLed() {
        ledPin.high();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ledPin.low();
    }
}

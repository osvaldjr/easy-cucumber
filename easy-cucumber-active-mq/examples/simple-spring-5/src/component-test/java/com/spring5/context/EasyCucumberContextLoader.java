package com.spring5.context;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Component
@ComponentScan({"io.github.osvaldjr"})
public class EasyCucumberContextLoader {}

package io.github.osvaldjr.unit;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import io.github.glytching.junit.extension.random.RandomBeansExtension;

@ExtendWith({MockitoExtension.class, RandomBeansExtension.class})
public abstract class UnitTest {}

package br.community.component.test.confs;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import io.github.glytching.junit.extension.random.RandomBeansExtension;

@ExtendWith({MockitoExtension.class, RandomBeansExtension.class})
public abstract class UnitTest {}

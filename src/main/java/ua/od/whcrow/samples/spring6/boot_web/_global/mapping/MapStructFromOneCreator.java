package ua.od.whcrow.samples.spring6.boot_web._global.mapping;

import ua.od.whcrow.samples.spring6.boot_web._commons.mapping.FromOneCreator;

public interface MapStructFromOneCreator<SOURCE, TARGET> extends MapStructFromOneMapper<SOURCE, TARGET>, FromOneCreator<SOURCE, TARGET> {}

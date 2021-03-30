package com.coremedia.labs.plugins.adapters.coremedia.server;

interface CoreMediaContentHubConfiguration {
  String getUsername();
  String getPassword();
  String getIor();
  String getPath();
  String getIgnoredTypes();
}

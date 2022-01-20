package com.coremedia.labs.plugins.adapters.coremedia;

import java.util.List;

interface CoreMediaContentHubSettings {

  /**
   * CoreMedia Content Cloud username to use for the connection.
   */
  String getUsername();

  /**
   * CoreMedia Content Cloud password to use for the connection.
   */
  String getPassword();

  /**
   * Fully qualified IOR uri to use for the connection.
   */
  String getIor();

  /**
   * Repository path to use for the connection.
   */
  String getPath();

  /**
   * List of ignored content types.
   */
  List<String> getIgnoredTypes();

  /**
   * List of ignored property names that will not be copied when content is imported.
   */
  List<String> getIgnoredProperties();

  /**
   * Display name for the adapter root folder.
   */
  String getDisplayName();
}

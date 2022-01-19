import ContentHub_properties from "@coremedia/studio-client.main.content-hub-editor-components/ContentHub_properties";
import CopyResourceBundleProperties from "@coremedia/studio-client.main.editor-components/configuration/CopyResourceBundleProperties";
import StudioPlugin from "@coremedia/studio-client.main.editor-components/configuration/StudioPlugin";
import Config from "@jangaroo/runtime/Config";
import ConfigUtils from "@jangaroo/runtime/ConfigUtils";
import resourceManager from "@jangaroo/runtime/l10n/resourceManager";
import ContentHubCoreMedia_properties from "./ContentHubCoreMedia_properties";

interface ContentHubCoreMediaStudioPluginConfig extends Config<StudioPlugin> {
}

class ContentHubCoreMediaStudioPlugin extends StudioPlugin {
  declare Config: ContentHubCoreMediaStudioPluginConfig;

  static readonly xtype: string = "com.coremedia.labs.plugins.adapters.coremedia.ContentHubCoreMediaStudioPlugin";

  constructor(config: Config<ContentHubCoreMediaStudioPlugin> = null) {
    super(ConfigUtils.apply(Config(ContentHubCoreMediaStudioPlugin, {
      configuration: [
        new CopyResourceBundleProperties({
          destination: resourceManager.getResourceBundle(null, ContentHub_properties),
          source: resourceManager.getResourceBundle(null, ContentHubCoreMedia_properties),
        }),
      ],
    }), config));
  }
}

export default ContentHubCoreMediaStudioPlugin;

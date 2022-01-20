import CoreIcons_properties from "@coremedia/studio-client.core-icons/CoreIcons_properties";

/**
 * Interface values for ResourceBundle "ContentHubCoreMedia".
 * @see ContentHubCoreMedia_properties#INSTANCE
 */
interface ContentHubCoreMedia_properties {
  /**
   * Adapter
   */
  adapter_type_coremedia_name: string;
  adapter_type_coremedia_icon: string;

  /**
   * Folders
   */
  folder_type_coremediaFolder_name: string;
  folder_type_coremediaFolder_icon: string;

  /**
   * Details panel
   */
  id_sectionItemKey: string;
  lastModified_sectionItemKey: string;
  metadata_sectionName: string;
  name_sectionItemKey: string;
  type_sectionItemKey: string;

  /**
   * Column headers
   */
  status_header: string;
  created_header: string;
}

/**
 * Singleton for the current user Locale's instance of ResourceBundle "ContentHubCoreMedia".
 * @see ContentHubCoreMedia_properties
 */
const ContentHubCoreMedia_properties:ContentHubCoreMedia_properties = {
  adapter_type_coremedia_icon: CoreIcons_properties.coremedia,
  adapter_type_coremedia_name: "CoreMedia",
  folder_type_coremediaFolder_icon: "",
  folder_type_coremediaFolder_name: "CoreMedia",
  id_sectionItemKey: "ID",
  lastModified_sectionItemKey: "Last modified",
  metadata_sectionName: "Metadata",
  name_sectionItemKey: "Name",
  type_sectionItemKey: "Type",
  status_header: "Status",
  created_header: "Created"
}

export default ContentHubCoreMedia_properties;

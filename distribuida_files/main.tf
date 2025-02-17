terraform {
  required_providers {
    azurerm = {
      source  = "hashicorp/azurerm"
      version = "4.18.0"
    }
  }
}

provider "azurerm" {
  features {}
  subscription_id = "14fe10cb-3cca-4f73-a96c-557c7e7319f4"
}

resource "azurerm_resource_group" "rg" {
  name     = "distribuida"
  location = "East US 2"
}

#--------------------------------------------------------------------
#-- Cluster AKS
resource "azurerm_kubernetes_cluster" "aks" {
  name                = "dbllumiquinga-cluster"
  location            = azurerm_resource_group.rg.location
  resource_group_name = azurerm_resource_group.rg.name
  dns_prefix          = "myaks"

  # Free control plane 
  sku_tier = "Free"
  
  default_node_pool {
    name       = "default"
    node_count = 1
    vm_size    = "Standard_B2s"
  }

  identity {
    type = "SystemAssigned"
  }

  tags = {
    environment = "dev"
  }
}

output "kube_config" {
  value     = azurerm_kubernetes_cluster.aks.kube_config_raw
  sensitive = true
}

#--------------------------------------------------------------------
#-- Database
resource "azurerm_postgresql_flexible_server" "pg" {
  name                   = "dbllumiquinga-pgserver"
  location               = azurerm_resource_group.rg.location
  resource_group_name    = azurerm_resource_group.rg.name
  version                = "16"
  sku_name               = "B_Standard_B1ms"  # Free tier (Burstable B1ms)
  storage_mb             = 32768  # Minimum required storage (32GB for free tier)
  administrator_login    = "distribuida"
  administrator_password = "VLAD753."

  backup_retention_days = 7
  geo_redundant_backup_enabled   = false

  authentication {
    active_directory_auth_enabled = false
  }
}

resource "azurerm_postgresql_flexible_server_database" "distribuida" {
  name      = "distribuida"
  server_id = azurerm_postgresql_flexible_server.pg.id
  charset = "UTF8"
}

resource "azurerm_postgresql_flexible_server_firewall_rule" "allow_all" {
  name             = "allow-all"
  server_id        = azurerm_postgresql_flexible_server.pg.id
  start_ip_address = "0.0.0.0"
  end_ip_address = "255.255.255.255"
}

output "pg_connection" {
  value = azurerm_postgresql_flexible_server.pg.fqdn
}

#--------------------------------------------------------------------

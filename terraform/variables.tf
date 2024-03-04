variable "username" {
  description = "The username for the database"
  type        = string
}

variable "password" {
  description = "The password for the database"
  type        = string
}

variable "postgres_version" {
  description = "The version of PostgreSQL to use"
  type        = string
  default     = "16.0"
}

variable "aws_resource_owner" {
  description = "The owner of the AWS resources"
  type        = string
}
variable "rds_instance_identifier" {
  description = "The identifier for the RDS instance"
  type        = string

}

variable "vpc-name" {
  description = "The name of the VPC"
  type        = string
  default     = "free-the-beans-vpc"
}

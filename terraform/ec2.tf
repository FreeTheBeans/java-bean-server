module "ec2-instance" {
  source  = "terraform-aws-modules/ec2-instance/aws"
  version = "5.6.0"

  name = aws_key_pair.generated_key.key_name

  instance_type = "t2.micro"
  key_name      = var.ec2-key-name
  #   monitoring             = true
  vpc_security_group_ids = [aws_security_group.free_the_beans_ec2_sg.id]
  subnet_id              = module.vpc.public_subnets[0]

#   launch_template = ""
#   user_data       = ""

}

resource "aws_security_group" "free_the_beans_ec2_sg" {
  name_prefix = "free-the-beans-ec2-"

  vpc_id = module.vpc.vpc_id
  ingress {
    from_port   = 443
    to_port     = 443
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }
  ingress {
    from_port   = 22
    to_port     = 22
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }
  ingress {
    from_port   = 80
    to_port     = 80
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

resource "tls_private_key" "private_key" {
  algorithm = "RSA"
  rsa_bits  = 4096
}

resource "aws_key_pair" "generated_key" {
  key_name   = var.ec2-key-name
  public_key = tls_private_key.private_key.public_key_openssh
}

resource "aws_ssm_parameter" "private_key_param" {
  name  = "private_key_free_the_beans_ec2"
  type  = "SecureString"
  value = tls_private_key.private_key.private_key_pem
}
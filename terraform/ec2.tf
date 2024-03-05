module "ec2-instance" {
  source  = "terraform-aws-modules/ec2-instance/aws"
  version = "5.6.0"

  name = var.ec2-instance-name

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

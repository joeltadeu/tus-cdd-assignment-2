#!/bin/bash
echo "Starting Docker installation..."
sudo yum update -y
sudo yum install -y docker
sudo systemctl start docker
sudo systemctl enable docker
sudo usermod -aG docker $USER
sudo yum install -y git wget
echo "Docker version:"
docker --version
echo "Docker status:"
sudo systemctl status docker --no-pager
echo ""
echo "Installation complete! Please log out and log back in."
echo "Then test with: docker ps"
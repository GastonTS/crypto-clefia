cryptol = IO.popen('cryptol', 'r+')

cryptol.puts ':l TpFar.cry'

76.times do puts cryptol.gets end

file = File.open(ARGV[0])
key = ARGV[1]

def bin_to_hex(s)
  "0x#{s.map { |b| b.to_s(16) }.join}"
end
puts bin_to_hex(file)

header = bin_to_hex(file.binmode.each_byte.take 54)
image = bin_to_hex(file.binmode.each_byte.drop 54)

cryptol.puts "encryptBig #{image} #{key}"
result = cryptol.gets[/(0x.*)/]

file.close
File.open('result', 'w+') do |f|
   f.write(header)
   f.write(result)
   f.close
end

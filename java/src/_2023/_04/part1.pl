perl -E '
	$score = 0; 
	while($line = <>){
		if ($line =~ /:\s*([^|]+)\s+\|\s*(.+)\n/g){
			@left_numbers = split " ", $1;
			@right_numbers = split " ", $2;
			$count = grep { $_ ~~ @right_numbers } @left_numbers;
			if($count > 0){
				$score += 2**($count-1);
			}
			
		}
	}
	say "$score\n";
' input.txt
